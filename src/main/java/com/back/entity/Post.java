package com.back.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

import java.util.List;

import com.back.jpa.entity.BaseIdAndTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Post extends BaseIdAndTime {
	@ManyToOne(fetch = LAZY)
	private Member author;

	private String title;

	@Column(columnDefinition = "LONGTEXT")
	private String content;

	@OneToMany(mappedBy = "post", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
	private List<PostComment> comments;

	public Post(Member author, String title, String content) {
		this.author = author;
		this.title = title;
		this.content = content;
	}

	public boolean hasComments() {
		return !comments.isEmpty();
	}

	public PostComment addComment(Member author, String content) {
		PostComment postComment = new PostComment(this, author, content);

		comments.add(postComment);

		return postComment;
	}

}
