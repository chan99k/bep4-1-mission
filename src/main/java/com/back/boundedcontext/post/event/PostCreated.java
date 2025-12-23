package com.back.boundedcontext.post.event;

import java.time.LocalDateTime;

public record PostCreated(
	int id,
	LocalDateTime createDate,
	LocalDateTime modifyDate,
	int authorId,
	String authorName,
	String title,
	String content
) implements PostEvent{
}
