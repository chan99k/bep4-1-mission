package com.back.boundedcontext.market.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.market.domain.Cart;
import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.out.CartRepository;
import com.back.boundedcontext.market.out.MarketMemberRepository;
import com.back.global.rsdata.RsData;
import com.back.shared.market.dto.MarketMemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class MarketCreateCartUseCase {
	private final CartRepository cartRepository;
	private final MarketMemberRepository marketMemberRepository;

	public RsData<Cart> createCart(MarketMemberDto buyer) {
		MarketMember marketMember = marketMemberRepository.getReferenceById(buyer.id());
		Cart cart = cartRepository.save(new Cart(marketMember));

		return new RsData<>(
			"201-1", "장바구니가 생성되었습니다", cart
		);
	}
}
