package com.back.global.eventpublisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.shared.standard.model.type.CanGetModelTypeCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;
	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void publishEvent(Object event) {
		applicationEventPublisher.publishEvent(event);
		log.debug("[Event] event: {}가 발행되었습니다.", event);
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void publishExternal(CanGetModelTypeCode event) {
		try {
			kafkaTemplate.send(event.getModelTypeCode(), event);
		} catch (Exception e) {
			log.error("[Kafka] 이벤트 발송에 실패하였습니다. topic: {}, event: {}, error: {}",
				event.getModelTypeCode(), event, e.getMessage(), e);
		}
	}
}
