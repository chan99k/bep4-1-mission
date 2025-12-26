package com.back.boundedcontext.post.domain;

import static jakarta.persistence.CascadeType.*;

import java.util.List;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.post.dto.PostCommentDto;
import com.back.shared.post.event.PostCommentCreated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "POST_POST")
public class Post extends BaseIdAndTime {

	@ManyToOne(fetch = FetchType.LAZY)
	private PostMember author;

	private String title;

	@Column(columnDefinition = "LONGTEXT")
	private String content;

	@OneToMany(mappedBy = "post", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
	private List<PostComment> comments;

	public Post(PostMember author, String title, String content) {
		this.author = author;
		this.title = title;
		this.content = content;
	}

	public boolean hasComments() {
		return !comments.isEmpty();
	}

	public PostComment addComment(PostMember author, String content) {
		PostComment postComment = new PostComment(this, author, content);

		comments.add(postComment);

		this.registerEvent(new PostCommentCreated(new PostCommentDto(postComment)));

		return postComment;
	}

}
