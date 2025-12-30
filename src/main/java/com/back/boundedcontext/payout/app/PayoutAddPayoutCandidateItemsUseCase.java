package com.back.boundedcontext.payout.app;

import java.util.List;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.payout.out.PayoutMemberRepository;
import com.back.boundedcontext.payout.out.PayoutRepository;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.dto.OrderItemDto;
import com.back.shared.market.out.MarketApiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutAddPayoutCandidateItemsUseCase {
	private final PayoutMemberRepository payoutMemberRepository;
	private final PayoutRepository payoutRepository;
	private final MarketApiClient marketApiClient;

	public void addPayoutCandidateItems(OrderDto order) {
		List<OrderItemDto> items = marketApiClient.getOrderItems(order.id());
		// TODO:: orderDTO 에 판매자 정보도 있어야 할듯

		items.forEach(item -> {
			log.debug("orderItem.id : {}", item.id());
		});
	}
}
