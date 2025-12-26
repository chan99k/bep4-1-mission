package com.back.boundedcontext.cash.in;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.boundedcontext.cash.app.CashFacade;
import com.back.shared.member.event.MemberJoined;
import com.back.shared.member.event.MemberModified;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CashEventListener {
	private final CashFacade cashFacade;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MemberJoined event) {
		cashFacade.syncMember(event.member());
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MemberModified event) {
		cashFacade.syncMember(event.member());
	}

}
