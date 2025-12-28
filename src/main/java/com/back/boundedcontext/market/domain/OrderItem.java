package com.back.boundedcontext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MARKET_ORDER_ITEM")
@NoArgsConstructor
public class OrderItem extends BaseIdAndTime {
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	private String name;
	private long price;
	private long salePrice;
	private final double payoutRate = MarketPolicy.PRODUCT_PAYOUT_RATE;

	public OrderItem(Order order, Product product, String name, long price, long salePrice) {
		this.order = order;
		this.product = product;
		this.name = name;
		this.price = price;
		this.salePrice = salePrice;
	}
}
