package com.back.boundedcontext.payout.in;

import static org.springframework.transaction.annotation.Propagation.*;
import static org.springframework.transaction.event.TransactionPhase.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.boundedcontext.payout.app.PayoutFacade;
import com.back.shared.market.event.MarketOrderPaymentCompleted;
import com.back.shared.member.event.MemberJoined;
import com.back.shared.member.event.MemberModified;
import com.back.shared.payout.event.PayoutCompleted;
import com.back.shared.payout.event.PayoutMemberCreated;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PayoutEventListener {
	private final PayoutFacade payoutFacade;

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MemberJoined event) {
		payoutFacade.syncMember(event.member());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MemberModified event) {
		payoutFacade.syncMember(event.member());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(PayoutMemberCreated event) {
		payoutFacade.createPayout(event.memberDto().id());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MarketOrderPaymentCompleted event) {
		payoutFacade.addPayoutCandidateItems(event.order());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(PayoutCompleted event) {
		payoutFacade.createPayout(event.payoutDto().payeeId());
	}
}
