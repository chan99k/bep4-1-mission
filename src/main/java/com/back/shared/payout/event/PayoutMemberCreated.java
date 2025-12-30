package com.back.shared.payout.event;

import com.back.shared.payout.dto.PayoutMemberDto;

public record PayoutMemberCreated(PayoutMemberDto memberDto) implements PayoutEvent {
}
