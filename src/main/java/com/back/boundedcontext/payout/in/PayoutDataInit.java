package com.back.boundedcontext.payout.in;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.payout.app.PayoutFacade;
import com.back.boundedcontext.payout.app.PayoutPolicy;
import com.back.shared.standard.ut.Util;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class PayoutDataInit {
	private final PayoutDataInit self;
	private final PayoutFacade payoutFacade;
	private final JobOperator jobOperator;
	private final Job payoutCollectItemsAndCompletePayoutsJob;

	public PayoutDataInit(
		@Lazy PayoutDataInit self,
		PayoutFacade payoutFacade, JobOperator jobOperator, Job payoutCollectItemsAndCompletePayoutsJob
	) {
		this.self = self;
		this.payoutFacade = payoutFacade;
		this.jobOperator = jobOperator;
		this.payoutCollectItemsAndCompletePayoutsJob = payoutCollectItemsAndCompletePayoutsJob;
	}

	@Bean
	@Order(4)
	public ApplicationRunner payoutDataInitApplicationRunner() {
		return args -> {
			self.forceMakePayoutReadyCandidatesItems();
			runCollectItemsAndCompletePayoutsBatchJob();
		};
	}

	/**
	 * 정산 후보 항목들을 정산 가능 상태(Ready)로 강제 변경
	 * <p>
	 * 테스트 또는 초기 데이터 구축을 위해 정산 후보 항목들의 결제 날짜를
	 * 정산 대기 기간(PAYOUT_READY_WAITING_DAYS) 이전으로 강제 설정
	 * </p>
	 */
	@Transactional
	public void forceMakePayoutReadyCandidatesItems() {
		payoutFacade.findPayoutCandidateItems().forEach(item -> {
			Util.reflection.setField(
				item,
				"paymentDate",
				LocalDateTime.now().minusDays(PayoutPolicy.PAYOUT_READY_WAITING_DAYS + 1)
			);
		});
	}

	@Transactional
	public void collectPayoutItemsMore() {
		payoutFacade.collectPayoutItemsMore(2);
	}

	@Transactional
	public void completePayoutsMore() {
		payoutFacade.completePayoutsMore(1);
	}

	private void runCollectItemsAndCompletePayoutsBatchJob() {
		JobParameters jobParameters = new JobParametersBuilder()
			.addString(
				"runDateTime",
				LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
			)
			.toJobParameters();

		try {
			jobOperator.start(payoutCollectItemsAndCompletePayoutsJob, jobParameters);
		} catch (JobInstanceAlreadyCompleteException e) {
			log.error("Job instance already complete", e);
		} catch (JobExecutionAlreadyRunningException e) {
			log.error("Job execution already running", e);
		} catch (InvalidJobParametersException e) {
			log.error("Invalid job parameters", e);
		} catch (JobRestartException e) {
			log.error("job restart exception", e);
		}
	}


}
