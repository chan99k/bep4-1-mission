package com.back.global.config;

import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonKafkaJsonSerializer<T> implements Serializer<T> {

	private final ObjectMapper objectMapper;
	private boolean addTypeInfo = true;

	public JacksonKafkaJsonSerializer(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		if (configs.containsKey("spring.json.add.type.headers")) {
			this.addTypeInfo = Boolean.parseBoolean(configs.get("spring.json.add.type.headers").toString());
		}
	}

	@Override
	public byte[] serialize(String topic, T data) {
		return serialize(topic, null, data);
	}

	@Override
	public byte[] serialize(String topic, Headers headers, T data) {
		if (data == null) {
			return null;
		}

		try {
			if (headers != null && addTypeInfo) {
				headers.add("__TypeId__", data.getClass().getName().getBytes());
			}
			return objectMapper.writeValueAsBytes(data);
		} catch (Exception e) {
			throw new SerializationException("Error serializing object to JSON", e);
		}
	}

	@Override
	public void close() {
	}
}