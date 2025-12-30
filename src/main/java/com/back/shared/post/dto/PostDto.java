package com.back.shared.post.dto;

public record PostDto(
	int id,
	String title,
	String author,
	String content
) {
}
