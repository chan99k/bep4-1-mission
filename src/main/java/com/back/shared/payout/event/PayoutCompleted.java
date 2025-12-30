package com.back.shared.payout.event;

import com.back.shared.payout.dto.PayoutDto;

public record PayoutCompleted(PayoutDto payoutDto) implements PayoutEvent{
}
