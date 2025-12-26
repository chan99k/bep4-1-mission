package com.back.boundedcontext.post.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.domain.PostComment;
import com.back.boundedcontext.post.out.PostRepository;
import com.back.global.eventpublisher.EventPublisher;
import com.back.global.rsdata.RsData;
import com.back.shared.member.dto.MemberBasicInfo;
import com.back.shared.member.out.MemberApiClient;
import com.back.shared.post.event.PostCommentCreated;
import com.back.shared.post.event.PostCreated;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostWriteUseCase { // NOTE :: 유스케이스는 인터페이스여야 하지 않나?
	private final PostRepository postRepository;
	private final MemberApiClient memberApiClient;
	private final EventPublisher eventPublisher;

	public long count() {
		return postRepository.count();
	}

	// NOTE :: 공통 응답 형식으로 바꾸는 것은 파사드 영역의 횡단 관심사가 아닌지? 유스케이스가 왜 알아야 하나요?
	public RsData<Post> write(int authorId, String title, String content) {
		Post saved = postRepository.save(new Post(authorId, title, content));

		eventPublisher.publishEvent(new PostCreated(
			saved.getId(),
			saved.getCreateDate(),
			saved.getModifyDate(),
			saved.getAuthorId(),
			"",
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

	public void addComment(int postId, int memberId, String comment) {
		MemberBasicInfo memberBasicInfo = memberApiClient.findPresentMemberId(memberId);
		Post post = postRepository.findById(postId).orElseThrow();
		PostComment postComment = post.addComment(memberBasicInfo.memberId(), memberBasicInfo.nickname(), comment);
		eventPublisher.publishEvent(PostCommentCreated.from(postComment));
	}
}
