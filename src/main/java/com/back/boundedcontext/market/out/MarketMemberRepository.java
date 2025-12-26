package com.back.boundedcontext.market.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.market.domain.MarketMember;

public interface MarketMemberRepository extends JpaRepository<MarketMember, Integer> {
	Optional<MarketMember> findByUsername(String username);
}
