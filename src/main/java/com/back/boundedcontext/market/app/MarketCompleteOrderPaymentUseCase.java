package com.back.boundedcontext.market.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.market.domain.Order;
import com.back.boundedcontext.market.out.OrderRepository;
import com.back.shared.market.dto.OrderDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketCompleteOrderPaymentUseCase {
	private final OrderRepository orderRepository;

	public void completeOrderPayment(OrderDto orderDto) {
		Order order = orderRepository.findById(orderDto.id()).get();
		order.completePayment();
		orderRepository.save(order);
	}
}
