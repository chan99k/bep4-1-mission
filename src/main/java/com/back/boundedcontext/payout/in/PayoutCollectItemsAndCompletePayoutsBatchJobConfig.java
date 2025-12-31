package com.back.boundedcontext.payout.in;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.back.boundedcontext.payout.app.PayoutFacade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class PayoutCollectItemsAndCompletePayoutsBatchJobConfig {
	private static final int CHUNK_SIZE = 10;

	private final PayoutFacade payoutFacade;

	public PayoutCollectItemsAndCompletePayoutsBatchJobConfig(PayoutFacade payoutFacade) {
		this.payoutFacade = payoutFacade;
	}

	@Bean
	public Job payoutCollectItemsJob(
		JobRepository jobRepository,
		Step payoutCollectItemsStep
	) {
		return new JobBuilder("payoutCollectItemsAndCompletePayoutsJob", jobRepository)
			.start(payoutCollectItemsStep)
			.build();
	}

	/**
	 * 정산 항목을 수집하는 배치 스텝을 정의
	 * CHUNK_SIZE 만큼의 정산 후보 항목을 정산 데이터로 변환하며, 처리할 항목이 남아있는 동안 반복해서 수행
	 *
	 * @param jobRepository 배치 작업의 상태를 기록하는 저장소
	 * @return 설정된 Step 객체
	 */
	@Bean
	public Step payoutCollectItemsStep(JobRepository jobRepository) {
		return new StepBuilder("payoutCollectItemsStep", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				Integer processedCount = payoutFacade.collectPayoutItemsMore(CHUNK_SIZE).getData();

				if (processedCount == null || processedCount == 0) {
					return RepeatStatus.FINISHED;
				}

				contribution.incrementWriteCount(processedCount);

				return RepeatStatus.CONTINUABLE;
			})
			.build();
	}

	@Bean
	public Step payoutCompletePayouts(JobRepository jobRepository) {
		return new StepBuilder("payoutCompletePayouts", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				int processedCount = payoutFacade.completePayoutsMore(CHUNK_SIZE).getData();

				if (processedCount == 0) {
					return RepeatStatus.FINISHED;
				}

				contribution.incrementWriteCount(processedCount);

				return RepeatStatus.CONTINUABLE;
			})
			.build();
	}
}
