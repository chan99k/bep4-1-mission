package com.back.boundedcontext.post.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.domain.PostComment;
import com.back.boundedcontext.post.domain.PostMember;
import com.back.boundedcontext.post.out.PostMemberRepository;
import com.back.boundedcontext.post.out.PostRepository;
import com.back.global.eventpublisher.EventPublisher;
import com.back.global.rsdata.RsData;
import com.back.shared.member.out.MemberApiClient;
import com.back.shared.post.dto.PostCommentDto;
import com.back.shared.post.event.PostCommentCreated;
import com.back.shared.post.event.PostCreated;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostWriteUseCase {
	private final PostRepository postRepository;
	private final MemberApiClient memberApiClient;
	private final EventPublisher eventPublisher;
	private final PostMemberRepository postMemberRepository;

	public long count() {
		return postRepository.count();
	}

	public RsData<Post> write(PostMember postMember, String title, String content) {

		Post saved = postRepository.save(
			new Post(postMember, title, content)
		);

		eventPublisher.publishEvent(new PostCreated(
			saved.getId(),
			saved.getCreateDate(),
			saved.getModifyDate(),
			saved.getAuthor().getId(),
			saved.getAuthor().getUsername(),
			title,
			content
		));

		String randomSecureTip = memberApiClient.getRandomSecureTip();

		return new RsData<>(
			"201-1",
			"%d번 글이 생성되었습니다. 보안 팁 : %s"
				.formatted(saved.getId(), randomSecureTip),
			saved
		);
	}

	public RsData<PostComment> addComment(Post post, PostMember member, String content) {
		PostComment postComment = post.addComment(member, content);

		String randomSecureTip = memberApiClient.getRandomSecureTip();

		eventPublisher.publishEvent(new PostCommentCreated(
			new PostCommentDto(
				postComment.getId(),
				postComment.getCreateDate(),
				postComment.getModifyDate(),
				postComment.getPost().getId(),
				postComment.getAuthor().getId(),
				postComment.getAuthor().getUsername(),
				postComment.getContent()
			)
		));

		return new RsData<>(

			"201-2",
			"%d번 댓글이 생성되었습니다. 보안 팁 : %s"
				.formatted(postComment.getId(), randomSecureTip),
			postComment
		);
	}
}
