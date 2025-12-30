package com.back.boundedcontext.payout.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.payout.domain.Payout;
import com.back.boundedcontext.payout.out.PayoutMemberRepository;
import com.back.boundedcontext.payout.out.PayoutRepository;
import com.back.shared.market.dto.OrderDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutAddPayoutCandidateItemsUseCase {
	private final PayoutMemberRepository payoutMemberRepository;
	private final PayoutRepository payoutRepository;

	public void addPayoutCandidateItems(OrderDto order) {
		Payout payout = payoutRepository.findById(order.id()).get();
		log.debug("addPayoutCandidateItems.order: {}", order.id());
	}
}
