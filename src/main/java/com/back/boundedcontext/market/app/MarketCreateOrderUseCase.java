package com.back.boundedcontext.market.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.market.domain.Cart;
import com.back.boundedcontext.market.domain.Order;
import com.back.boundedcontext.market.out.OrderRepository;
import com.back.global.rsdata.RsData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketCreateOrderUseCase {
	private final OrderRepository orderRepository;

	public RsData<Order> createOrder(Cart cart) {
		Order _order = new Order(cart);

		Order order = orderRepository.save(_order);

		cart.clearItems();

		return new RsData<>(
			"201-1",
			"%d번 주문이 생성되었습니다.".formatted(order.getId()),
			order
		);
	}
}
