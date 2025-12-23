package com.back.boundedcontext.post.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.member.entity.Member;
import com.back.boundedcontext.member.service.MemberService;
import com.back.boundedcontext.post.entity.Post;
import com.back.boundedcontext.post.entity.PostComment;
import com.back.boundedcontext.post.event.PostCommentCreated;
import com.back.boundedcontext.post.event.PostCreated;
import com.back.boundedcontext.post.repository.PostRepository;
import com.back.global.EventPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final MemberService memberService;
	private final EventPublisher eventPublisher;

	public long count() {
		return postRepository.count();
	}

	public void write(Member author, String title, String content) {
		Post post = new Post(author, title, content);

		Post saved = postRepository.save(post);

		eventPublisher.publishEvent(new PostCreated(
			saved.getId(),
			saved.getCreateDate(),
			saved.getModifyDate(),
			saved.getAuthor().getId(),
			saved.getAuthor().getUsername(),
			title,
			content
		));
	}

	public Optional<Post> findById(int id) {
		return postRepository.findById(id);
	}

	public void addComment(int postId, int memberId, String comment) {
		Member member = memberService.findById(memberId).orElseThrow();
		Post post = postRepository.findById(postId).orElseThrow();
		PostComment postComment = post.addComment(member, comment);
		eventPublisher.publishEvent(PostCommentCreated.from(postComment));
	}
}
