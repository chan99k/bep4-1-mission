package com.back.boundedcontext.market.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.market.domain.Cart;
import com.back.boundedcontext.market.domain.MarketMember;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	Optional<Cart> findByBuyer(MarketMember buyer);
}
