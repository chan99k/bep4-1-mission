package com.back.boundedcontext.market.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.market.domain.Order;
import com.back.boundedcontext.market.out.OrderRepository;
import com.back.shared.cash.event.CashOrderPaymentFailed;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketCancelOrderRequestPaymentUseCase {
	private final OrderRepository orderRepository;

	public void handle(CashOrderPaymentFailed event) {
		Order order = orderRepository.findById(event.order().id()).get();

		order.cancelRequestPayment();
	}
}
