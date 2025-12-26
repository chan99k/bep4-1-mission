package com.back.boundedcontext.post.domain;

import static jakarta.persistence.CascadeType.*;

import java.util.List;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.post.event.PostCommentCreated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseIdAndTime {

	private int authorId;

	private String title;

	@Column(columnDefinition = "LONGTEXT")
	private String content;

	@OneToMany(mappedBy = "post", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
	private List<PostComment> comments;

	public Post(int authorId, String title, String content) {
		this.authorId = authorId;
		this.title = title;
		this.content = content;
	}

	public boolean hasComments() {
		return !comments.isEmpty();
	}

	public PostComment addComment(int authorId, String authorNickname, String content) {
		PostComment postComment = new PostComment(this, authorId, authorNickname, content);

		comments.add(postComment);

		this.registerEvent(PostCommentCreated.from(postComment));

		return postComment;
	}

}
