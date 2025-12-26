package com.back.boundedcontext.post.in;

import static org.springframework.transaction.annotation.Propagation.*;
import static org.springframework.transaction.event.TransactionPhase.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.boundedcontext.post.app.PostFacade;
import com.back.shared.member.event.MemberJoined;
import com.back.shared.member.event.MemberModified;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostEventListener {
	private final PostFacade postFacade;

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MemberJoined event) {
		postFacade.syncMember(event.member());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MemberModified event) {
		postFacade.syncMember(event.member());
	}
}
