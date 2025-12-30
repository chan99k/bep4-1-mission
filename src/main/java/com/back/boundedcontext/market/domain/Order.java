package com.back.boundedcontext.market.domain;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.event.MarketOrderPaymentCompleted;
import com.back.shared.market.event.MarketOrderPaymentRequested;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MARKET_ORDER")
@NoArgsConstructor
@Getter
public class Order extends BaseIdAndTime {
	@ManyToOne(fetch = LAZY)
	private MarketMember buyer;

	private long price;

	private long salePrice;

	@OneToMany(mappedBy = "order", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
	private List<OrderItem> items = new ArrayList<>();

	private LocalDateTime requestPaymentDate;

	private LocalDateTime paymentDate;

	private LocalDateTime cancelDate;

	public Order(Cart cart) {
		this.buyer = cart.getBuyer();

		cart.getItems().forEach(item -> {
			addItem(item.getProduct());
		});
	}

	public void addItem(Product product) {
		OrderItem orderItem = new OrderItem(
			this,
			product,
			product.getName(),
			product.getPrice(),
			product.getSalePrice()
		);

		items.add(orderItem);

		price += product.getPrice();
		salePrice += product.getSalePrice();
	}

	public void completePayment() { // NOTE :: 왜 completePayment 라는 용어? completePaymentRequest 가 정확한 표현 아닌가?
		paymentDate = LocalDateTime.now();

		this.registerEvent(
			new MarketOrderPaymentCompleted(
				toDto()
			)
		);
	}

	public boolean isPaid() {
		return paymentDate != null;
	}

	public boolean isCanceled() {
		return cancelDate != null;
	}

	public boolean isPaymentInProgress() {
		return requestPaymentDate != null && paymentDate == null && cancelDate == null;
	}

	public void requestPayment(long pgPaymentAmount) {
		requestPaymentDate = LocalDateTime.now();

		registerEvent(
			new MarketOrderPaymentRequested(
				this.toDto(),
				pgPaymentAmount
			)
		);
	}

	public void cancelRequestPayment() {
		requestPaymentDate = null;
	}

	public OrderDto toDto() {
		return new OrderDto(
			this.getId(),
			this.getCreateDate(), this.getModifyDate(),
			this.getBuyer().getId(),
			this.getBuyer().getUsername(),
			this.getPrice(),
			this.getSalePrice(),
			this.getRequestPaymentDate(),
			this.getPaymentDate()
		);
	}
}
