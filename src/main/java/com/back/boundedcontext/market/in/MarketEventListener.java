package com.back.boundedcontext.market.in;

import static org.springframework.transaction.annotation.Propagation.*;
import static org.springframework.transaction.event.TransactionPhase.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.boundedcontext.market.app.MarketFacade;
import com.back.shared.cash.event.CashOrderPaymentFailed;
import com.back.shared.cash.event.CashOrderPaymentSucceeded;
import com.back.shared.market.event.MarketMemberCreated;
import com.back.shared.member.event.MemberJoined;
import com.back.shared.member.event.MemberModified;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MarketEventListener {

	private final MarketFacade marketFacade;

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MemberJoined event) {
		marketFacade.syncMember(event.member());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MemberModified event) {
		marketFacade.syncMember(event.member());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MarketMemberCreated event) {
		marketFacade.createCart(event.member());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(CashOrderPaymentSucceeded event) {
		marketFacade.handle(event);
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(CashOrderPaymentFailed event) {
		marketFacade.handle(event);
	}
}
