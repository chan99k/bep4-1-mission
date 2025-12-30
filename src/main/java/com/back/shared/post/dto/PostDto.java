package com.back.shared.post.dto;

import com.back.shared.standard.model.type.CanGetModelTypeCode;

public record PostDto(
	int id,
	String title,
	String author,
	String content
) implements CanGetModelTypeCode {
	@Override
	public String getModelTypeCode() {
		return "Post";
	}
}
