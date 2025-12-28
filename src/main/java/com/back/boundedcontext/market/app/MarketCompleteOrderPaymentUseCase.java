package com.back.boundedcontext.market.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.market.domain.Order;
import com.back.boundedcontext.market.out.OrderRepository;
import com.back.shared.cash.event.CashOrderPaymentSucceeded;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketCompleteOrderPaymentUseCase {
	private final OrderRepository orderRepository;

	public void handle(CashOrderPaymentSucceeded event) {
		Order order = orderRepository.findById(event.order().id()).get();

		order.completePayment();
	}
}
