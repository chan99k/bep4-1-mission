package com.back.shared.market.event;

import com.back.shared.market.dto.OrderDto;

public record MarketOrderPaymentRequested(
	OrderDto order,
	long pgPaymentAmount
) {
}
