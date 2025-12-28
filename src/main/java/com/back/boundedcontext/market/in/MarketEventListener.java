package com.back.boundedcontext.market.in;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.boundedcontext.market.app.MarketFacade;
import com.back.shared.market.event.MarketMemberCreated;
import com.back.shared.member.event.MemberJoined;
import com.back.shared.member.event.MemberModified;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MarketEventListener {

	private final MarketFacade marketFacade;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MemberJoined event) {
		marketFacade.syncMember(event.member());
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MemberModified event) {
		marketFacade.syncMember(event.member());
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MarketMemberCreated event) {
		marketFacade.createCart(event.member());
	}
}
