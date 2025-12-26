package com.back.boundedcontext.post.in;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.boundedcontext.post.app.PostFacade;
import com.back.shared.member.event.MemberJoinedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostEventListener {
	private final PostFacade postFacade;

	@TransactionalEventListener(MemberJoinedEvent.class)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MemberJoinedEvent event) {
		postFacade.syncMember(event.member());
	}
}
