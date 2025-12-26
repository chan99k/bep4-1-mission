package com.back.boundedcontext.post.domain;

import static jakarta.persistence.FetchType.*;

import com.back.global.jpa.entity.BaseIdAndTime;

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

	private int authorId;

	private String authorNickname;

	@Column(columnDefinition = "TEXT")
	private String content;

	public PostComment(Post parent, int authorId, String authorNickname, String content) {
		super();
		this.content = content;
		this.authorId = authorId;
		this.authorNickname = authorNickname;
		this.post = parent;
	}
}
