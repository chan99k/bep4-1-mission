package com.back.shared.post.dto;

import com.back.boundedcontext.post.domain.Post;

public record PostDto(
	int id,
	String title,
	String author,
	String content
) {
	public static PostDto from(Post post
	) {
		return new PostDto(
			post.getId(),
			post.getTitle(),
			post.getAuthor().getUsername(),
			post.getContent()
		);
	}
}
