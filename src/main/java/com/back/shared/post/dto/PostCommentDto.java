package com.back.shared.post.dto;

import java.time.LocalDateTime;

import com.back.boundedcontext.post.domain.PostComment;

public record PostCommentDto(
	int id,
	LocalDateTime createDate,
	LocalDateTime modifyDate,
	int postId,
	int authorId,
	String authorName,
	String content
) {
	public PostCommentDto(PostComment comment) {
		this(
			comment.getId(),
			comment.getCreateDate(),
			comment.getModifyDate(),
			comment.getPost().getId(),
			comment.getAuthor().getId(),
			comment.getAuthor().getUsername(),
			comment.getContent()
		);
	}
}
