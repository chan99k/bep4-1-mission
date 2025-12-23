package com.back.boundedcontext.post.entity;

import static jakarta.persistence.FetchType.*;

import com.back.boundedcontext.member.entity.Member;
import com.back.global.jpa.entity.BaseIdAndTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostComment extends BaseIdAndTime {

	@ManyToOne(fetch = LAZY)
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@Column(columnDefinition = "TEXT")
	private String content;

	public PostComment(Post parent, Member author, String content) {
		super();
		this.content = content;
		this.member = author;
		this.post = parent;
	}
}
