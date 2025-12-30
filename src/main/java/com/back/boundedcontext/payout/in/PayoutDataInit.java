package com.back.boundedcontext.payout.in;

import java.time.LocalDateTime;

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

	public PayoutDataInit(
		@Lazy PayoutDataInit self,
		PayoutFacade payoutFacade
	) {
		this.self = self;
		this.payoutFacade = payoutFacade;
	}

	@Bean
	@Order(4)
	public ApplicationRunner payoutDataInitApplicationRunner() {
		return args -> {
			self.forceMakePayoutReadyCandidatesItems();
			self.collectPayoutItemsMore();
		};
	}

	/**
	 * 정산 후보 항목들을 정산 가능 상태(Ready)로 강제 변경합니다.
	 * <p>
	 * 테스트 또는 초기 데이터 구축을 위해 정산 후보 항목들의 결제 날짜를
	 * 정산 대기 기간(PAYOUT_READY_WAITING_DAYS) 이전으로 강제 설정합니다.
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
		payoutFacade.collectPayoutItemsMore(4);
		payoutFacade.collectPayoutItemsMore(2);
		payoutFacade.collectPayoutItemsMore(2);
	}
}
