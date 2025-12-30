package com.back.boundedcontext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.market.dto.OrderItemDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MARKET_ORDER_ITEM")
@NoArgsConstructor
@Getter
public class OrderItem extends BaseIdAndTime {
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	private String name;
	private long price;
	private long salePrice;
	private double payoutRate;

	public OrderItem(
		Order order,
		Product product,
		String name,
		long price,
		long salePrice,
		double payoutRate
	) {
		this.order = order;
		this.product = product;
		this.name = name;
		this.price = price;
		this.salePrice = salePrice;
		this.payoutRate = payoutRate;
	}

	public OrderItemDto toDto() {
		long salePriceWithoutFee = Math.round(this.salePrice * this.payoutRate / 100);
		long payoutFee = this.salePrice - salePriceWithoutFee;
		return toDto(payoutFee, salePriceWithoutFee);
	}

	public OrderItemDto toDto(long payoutFee,
		long salePriceWithoutFee) {
		return new OrderItemDto(
			getId(),
			getCreateDate(),
			getModifyDate(),
			order.getId(),
			order.getBuyer().getId(),
			order.getBuyer().getNickname(),
			product.getSeller().getId(),
			product.getSeller().getNickname(),
			product.getId(),
			name,
			price,
			salePrice,
			payoutRate, payoutFee, salePriceWithoutFee
		);
	}

}
