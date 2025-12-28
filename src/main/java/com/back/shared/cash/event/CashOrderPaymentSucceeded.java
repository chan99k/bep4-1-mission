package com.back.shared.cash.event;

import com.back.shared.market.dto.OrderDto;

public record CashOrderPaymentSucceeded(OrderDto order, long pgPaymentAmount) {
}
