package com.back.boundedcontext.market.app;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.out.MarketMemberRepository;
import com.back.boundedcontext.market.out.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketSupport {
	private final ProductRepository productRepository;
	private final MarketMemberRepository marketMemberRepository;

	public long countProducts() {
		return productRepository.count();
	}

	public Optional<MarketMember> findMemberByUsername(String username) {
		return marketMemberRepository.findByUsername(username);
	}
}
