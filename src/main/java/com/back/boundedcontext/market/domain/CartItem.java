package com.back.boundedcontext.market.domain;

import static jakarta.persistence.FetchType.*;

import com.back.global.jpa.entity.BaseIdAndTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MARKET_CART_ITEM")
public class CartItem extends BaseIdAndTime {
	@ManyToOne(fetch = LAZY)
	private Cart cart;
	@ManyToOne(fetch = LAZY)
	private Product product;
}
