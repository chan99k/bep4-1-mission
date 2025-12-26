package com.back.shared.post.event;

import java.time.LocalDateTime;

import com.back.boundedcontext.post.domain.PostComment;

public record PostCommentCreated(
	int id,
	LocalDateTime createDate,
	LocalDateTime modifyDate,
	int postId,
	int authorId,
	String authorName,
	String content
) implements PostEvent {
	public static PostCommentCreated from(PostComment comment) {
		return new PostCommentCreated(
			comment.getId(),
			comment.getCreateDate(),
			comment.getModifyDate(),
			comment.getPost().getId(),
			comment.getAuthorId(),
			comment.getAuthorNickname(),
			comment.getContent()
		);
	}
}
