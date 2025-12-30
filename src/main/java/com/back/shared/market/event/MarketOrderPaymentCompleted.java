package com.back.shared.market.event;

import com.back.shared.market.dto.OrderDto;

public record MarketOrderPaymentCompleted(OrderDto order) implements MarketEvent {
}
