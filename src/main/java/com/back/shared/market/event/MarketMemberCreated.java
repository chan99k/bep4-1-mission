package com.back.shared.market.event;

import com.back.shared.market.dto.MarketMemberDto;

public record MarketMemberCreated(
	MarketMemberDto member
) implements MarketEvent {
}
