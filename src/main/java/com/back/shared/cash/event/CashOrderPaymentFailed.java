package com.back.shared.cash.event;

import com.back.shared.market.dto.OrderDto;

public record CashOrderPaymentFailed(
	String resultCode,
	String message,
	OrderDto order,
	long pgPaymentAmount,
	long l1
) {
}
