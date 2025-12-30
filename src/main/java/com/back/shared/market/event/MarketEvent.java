package com.back.shared.market.event;

public sealed interface MarketEvent permits MarketMemberCreated, MarketOrderPaymentCompleted {
}
