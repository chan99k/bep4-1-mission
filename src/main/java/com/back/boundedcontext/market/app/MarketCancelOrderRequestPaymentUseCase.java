package com.back.boundedcontext.market.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.market.domain.Order;
import com.back.boundedcontext.market.out.OrderRepository;
import com.back.shared.market.dto.OrderDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketCancelOrderRequestPaymentUseCase {
	private final OrderRepository orderRepository;

	public void cancelOrderRequestPayment(OrderDto dto) {
		Order order = orderRepository.findById(dto.id()).get();

		order.cancelRequestPayment();
	}
}
