package com.back.boundedcontext.payout.app;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.payout.domain.PayoutCandidateItem;
import com.back.boundedcontext.payout.domain.PayoutEventType;
import com.back.boundedcontext.payout.domain.PayoutMember;
import com.back.boundedcontext.payout.out.PayoutCandidateItemRepository;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.dto.OrderItemDto;
import com.back.shared.market.out.MarketApiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutAddPayoutCandidateItemsUseCase {
	private final PayoutCandidateItemRepository payoutCandidateItemRepository;
	private final PayoutSupport payoutSupport;
	private final MarketApiClient marketApiClient;

	public void addPayoutCandidateItems(OrderDto order) {
		marketApiClient.getOrderItems(order.id())
			.forEach(orderItem -> makePayoutCandidateItems(order, orderItem));
	}

	private void makePayoutCandidateItems(
		OrderDto order,
		OrderItemDto orderItem
	) {
		PayoutMember system = payoutSupport.findSystemMember().get();
		PayoutMember buyer = payoutSupport.findMemberById(orderItem.buyerId()).get();
		PayoutMember seller = payoutSupport.findMemberById(orderItem.sellerId()).get();

		makePayoutCandidateItem(
			PayoutEventType.정산__상품판매_수수료,
			orderItem.getModelTypeCode(),
			orderItem.id(),
			order.paymentDate(),
			buyer,
			system,
			orderItem.payoutFee()
		);

		makePayoutCandidateItem(
			PayoutEventType.정산__상품판매_대금,
			orderItem.getModelTypeCode(),
			orderItem.id(),
			order.paymentDate(),
			buyer,
			seller,
			orderItem.salePriceWithoutFee()
		);
	}

	private void makePayoutCandidateItem(
		PayoutEventType eventType,
		String relTypeCode,
		int relId,
		LocalDateTime paymentDate,
		PayoutMember payer,
		PayoutMember payee,
		long amount
	) {
		PayoutCandidateItem payoutCandidateItem = new PayoutCandidateItem(
			eventType,
			relTypeCode,
			relId,
			paymentDate,
			payer,
			payee,
			amount
		);

		payoutCandidateItemRepository.save(payoutCandidateItem);

	}
}
