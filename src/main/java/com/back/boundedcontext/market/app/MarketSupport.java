package com.back.boundedcontext.market.app;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.market.domain.Cart;
import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.domain.Order;
import com.back.boundedcontext.market.domain.Product;
import com.back.boundedcontext.market.out.CartRepository;
import com.back.boundedcontext.market.out.MarketMemberRepository;
import com.back.boundedcontext.market.out.OrderRepository;
import com.back.boundedcontext.market.out.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketSupport {
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;
	private final MarketMemberRepository marketMemberRepository;
	private final OrderRepository orderRepository;

	public long countProducts() {
		return productRepository.count();
	}

	public Optional<MarketMember> findMemberByUsername(String username) {
		return marketMemberRepository.findByUsername(username);
	}

	public Optional<Cart> findCartByBuyer(MarketMember buyer) {
		return cartRepository.findByBuyer(buyer);
	}

	public Optional<Product> findProductById(int id) {
		return productRepository.findById(id);
	}

	public long countOrders() {
		return orderRepository.count();
	}

	public Optional<Order> findOrderById(int id) {
		return orderRepository.findById(id);
	}

	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}
}
