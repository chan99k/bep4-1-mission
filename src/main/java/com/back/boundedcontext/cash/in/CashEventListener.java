package com.back.boundedcontext.cash.in;

import static org.springframework.transaction.annotation.Propagation.*;
import static org.springframework.transaction.event.TransactionPhase.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.boundedcontext.cash.app.CashFacade;
import com.back.shared.cash.event.CashMemberCreated;
import com.back.shared.market.event.MarketOrderPaymentRequested;
import com.back.shared.member.event.MemberJoined;
import com.back.shared.member.event.MemberModified;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CashEventListener {
	private final CashFacade cashFacade;

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MemberJoined event) {
		cashFacade.syncMember(event.member());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MemberModified event) {
		cashFacade.syncMember(event.member());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(CashMemberCreated event) {
		cashFacade.createWallet(event.member());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MarketOrderPaymentRequested event) {
		cashFacade.handle(event);
	}
}
