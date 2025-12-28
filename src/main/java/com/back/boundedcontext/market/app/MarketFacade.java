package com.back.boundedcontext.market.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.market.domain.Cart;
import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.domain.Order;
import com.back.boundedcontext.market.domain.Product;
import com.back.boundedcontext.market.out.OrderRepository;
import com.back.global.rsdata.RsData;
import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketFacade {
	private final MarketSyncMemberUseCase marketSyncMemberUseCase;
	private final MarketCreateProductUseCase marketCreateProductUseCase;
	private final MarketSupport marketSupport;
	private final MarketCreateCartUseCase marketCreateCartUseCase;
	private final MarketCreateOrderUseCase marketCreateOrderUseCase;
	private final OrderRepository orderRepository;

	@Transactional
	public MarketMember syncMember(MemberDto member) {
		return marketSyncMemberUseCase.syncMember(member);
	}

	@Transactional(readOnly = true)
	public long productsCount() {
		return marketSupport.countProducts();
	}

	@Transactional
	public Product createProduct(
		MarketMember seller,
		String sourceTypeCode,
		int sourceId,
		String name,
		String description,
		long price,
		long salePrice
	) {
		return marketCreateProductUseCase.createProduct(
			seller,
			sourceTypeCode,
			sourceId,
			name,
			description,
			price,
			salePrice
		);
	}

	@Transactional(readOnly = true)
	public Optional<MarketMember> findMemberByUsername(String username) {
		return marketSupport.findMemberByUsername(username);
	}

	@Transactional
	public RsData<Cart> createCart(MarketMemberDto buyer) {
		return marketCreateCartUseCase.createCart(buyer);
	}

	@Transactional(readOnly = true)
	public Optional<Cart> findCartByBuyer(MarketMember buyer) {
		return marketSupport.findCartByBuyer(buyer);
	}

	@Transactional(readOnly = true)
	public Optional<Product> findProductById(int id) {
		return marketSupport.findProductById(id);
	}

	public long ordersCount() {
		return marketSupport.countOrders();
	}

	public RsData<Order> createOrder(Cart cart) {
		return marketCreateOrderUseCase.createOrder(cart);
	}
}
