package com.back.boundedcontext.post.domain;

import static jakarta.persistence.FetchType.*;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.post.dto.PostCommentDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "POST_POST_COMMENT")
@NoArgsConstructor
public class PostComment extends BaseIdAndTime {

	@ManyToOne(fetch = LAZY)
	private Post post;

	@ManyToOne(fetch = LAZY)
	private PostMember author;

	@Column(columnDefinition = "TEXT")
	private String content;

	public PostComment(Post parent, PostMember author, String content) {
		super();
		this.content = content;
		this.author = author;
		this.post = parent;
	}

	public PostCommentDto toDto() {
		return new PostCommentDto(
			this.getId(),
			this.getCreateDate(),
			this.getModifyDate(),
			this.post.getId(), this.getAuthor().getId(),
			this.getAuthor().getUsername(),
			this.getContent()
		);
	}
}
