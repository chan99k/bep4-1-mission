package com.back.boundedcontext.market.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.market.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
