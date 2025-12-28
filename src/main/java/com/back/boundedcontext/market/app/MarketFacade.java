package com.back.boundedcontext.market.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.domain.Product;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketFacade {
	private final MarketSyncMemberUseCase marketSyncMemberUseCase;
	private final MarketCreateProductUseCase marketCreateProductUseCase;
	private final MarketSupport marketSupport;

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
		int price,
		int salePrice
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
}
