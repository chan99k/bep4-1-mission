package com.back.global.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import com.back.global.eventpublisher.EventPublisher;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class GlobalConfig {

	@Bean
	public EventPublisher eventPublisher(
		ApplicationEventPublisher eventPublisher,
		KafkaTemplate<String, Object> kafkaTemplate
	) {
		return new EventPublisher(eventPublisher, kafkaTemplate);
	}
}
