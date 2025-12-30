package com.back.shared.payout.event;

public sealed interface PayoutEvent permits PayoutCompleted, PayoutMemberCreated {
}
