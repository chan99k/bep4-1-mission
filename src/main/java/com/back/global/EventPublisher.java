package com.back.global;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;

	public void publishEvent(Object event) {
		applicationEventPublisher.publishEvent(event);
		log.debug("[Event] event: {}가 발행되었습니다.", event);
	}
}
