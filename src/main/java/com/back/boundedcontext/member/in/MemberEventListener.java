package com.back.boundedcontext.member.in;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.member.app.MemberFacade;
import com.back.shared.post.event.PostCommentCreated;
import com.back.shared.post.event.PostCreated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "Post", groupId = "member-post-cosumer-group")
public class MemberEventListener {
	private final MemberFacade memberFacade;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@KafkaHandler
	public void handle(PostCreated event) {
		try {
			memberFacade.increaseActivityScore(event.authorId(), 3);
		} catch (Exception e) {
			log.error("[Kafka] PostCreated 이벤트 처리에 실패하였습니다. event: {}, error: {}", event, e.getMessage(), e);
			throw e;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@KafkaHandler
	public void handle(PostCommentCreated event) {
		try {
			memberFacade.increaseActivityScore(event.postCommentDto().authorId(), 1);
		} catch (Exception e) {
			log.error("[Kafka] PostCommentCreated 이벤트 처리에 실패하였습니다. event: {}, error: {}", event, e.getMessage(), e);
			throw e;
		}
	}

	@KafkaHandler(isDefault = true)
	public void ignoreUnknown(Object unknownEvent) {
		log.warn("[Kafka] 알 수 없는 이벤트 수신: {}", unknownEvent);
	}
}
