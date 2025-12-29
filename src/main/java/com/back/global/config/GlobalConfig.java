package com.back.global.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.back.global.eventpublisher.EventPublisher;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class GlobalConfig {

	public static String INTERNAL_CALL_BACK_URL;

	@Bean
	public EventPublisher eventPublisher(ApplicationEventPublisher eventPublisher) {
		return new EventPublisher(eventPublisher);
	}
}
