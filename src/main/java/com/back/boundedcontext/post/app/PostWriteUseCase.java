package com.back.boundedcontext.post.app;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.member.app.MemberFacade;
import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.domain.PostComment;
import com.back.boundedcontext.post.out.PostRepository;
import com.back.global.eventpublisher.EventPublisher;
import com.back.shared.post.event.PostCommentCreated;
import com.back.shared.post.event.PostCreated;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostWriteUseCase {
	private final PostRepository postRepository;
	private final MemberFacade memberFacade;
	private final EventPublisher eventPublisher;

	public long count() {
		return postRepository.count();
	}

	public Post write(Member author, String title, String content) {
		Post saved = postRepository.save(new Post(author, title, content));

		eventPublisher.publishEvent(new PostCreated(
			saved.getId(),
			saved.getCreateDate(),
			saved.getModifyDate(),
			saved.getAuthor().getId(),
			saved.getAuthor().getUsername(),
			title,
			content
		));

		return saved;
	}

	public Optional<Post> findById(int id) {
		return postRepository.findById(id);
	}

	public void addComment(int postId, int memberId, String comment) {
		Member member = memberFacade.findById(memberId).orElseThrow();
		Post post = postRepository.findById(postId).orElseThrow();
		PostComment postComment = post.addComment(member, comment);
		eventPublisher.publishEvent(PostCommentCreated.from(postComment));
	}
}
