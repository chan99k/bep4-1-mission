package com.back.boundedcontext.member.in;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.boundedcontext.member.app.MemberFacade;
import com.back.boundedcontext.member.domain.Member;
import com.back.shared.post.event.PostCommentCreated;
import com.back.shared.post.event.PostCreated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MemberEventListener {
	private final MemberFacade memberFacade;

	private void increaseMemberActivityScore(int memberId, int score) {
		Member member = memberFacade.findById(memberId).orElseThrow();

		int before = member.getActivityScore();
		member.increaseActivityScore(score);
		int after = member.getActivityScore();

		log.debug("[Event] 사용자 활동 점수가 {} -> {} 로 증가하였습니다.", before, after);
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(PostCreated event) {
		increaseMemberActivityScore(event.authorId(), 3);
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(PostCommentCreated event) {
		increaseMemberActivityScore(event.authorId(), 1);
	}
}
