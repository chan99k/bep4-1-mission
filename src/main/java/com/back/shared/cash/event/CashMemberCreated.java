package com.back.shared.cash.event;

import com.back.shared.cash.dto.CashMemberDto;

public record CashMemberCreated(
	CashMemberDto member
) implements CashEvent {
}
