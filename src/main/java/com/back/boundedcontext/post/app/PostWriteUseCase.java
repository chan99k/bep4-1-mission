package com.back.boundedcontext.post.app;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.member.app.MemberFacade;
import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.domain.PostComment;
import com.back.boundedcontext.post.out.PostRepository;
import com.back.global.eventpublisher.EventPublisher;
import com.back.global.rsdata.RsData;
import com.back.shared.post.event.PostCommentCreated;
import com.back.shared.post.event.PostCreated;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostWriteUseCase { // NOTE :: 유스케이스는 인터페이스여야 하지 않나?
	private final PostRepository postRepository;
	private final MemberFacade memberFacade;
	private final EventPublisher eventPublisher;

	public long count() {
		return postRepository.count();
	}

	// NOTE :: 공통 응답 형식으로 바꾸는 것은 파사드 영역의 횡단 관심사가 아닌지? 유스케이스가 왜 알아야 하나요?
	public RsData<Post> write(Member author, String title, String content) {
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

		String randomSecureTip = memberFacade.getRandomSecureTip();

		return new RsData<>(
			"201-1",
			"%d번 글이 생성되었습니다. 보안 팁 : %s"
				.formatted(saved.getId(), randomSecureTip),
			saved
		);
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
