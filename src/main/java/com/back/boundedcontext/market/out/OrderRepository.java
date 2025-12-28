package com.back.boundedcontext.market.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.market.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
