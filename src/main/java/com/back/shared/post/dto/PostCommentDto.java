package com.back.shared.post.dto;

import java.time.LocalDateTime;

import com.back.shared.standard.model.type.CanGetModelTypeCode;

public record PostCommentDto(
	int id,
	LocalDateTime createDate,
	LocalDateTime modifyDate,
	int postId,
	int authorId,
	String authorName,
	String content
) implements CanGetModelTypeCode {
	@Override
	public String getModelTypeCode() {
		return "PostComment";
	}
}
