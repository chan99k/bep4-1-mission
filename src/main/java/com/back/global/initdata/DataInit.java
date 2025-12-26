package com.back.global.initdata;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.member.app.MemberFacade;
import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.post.app.PostFacade;
import com.back.boundedcontext.post.app.PostWriteUseCase;
import com.back.boundedcontext.post.domain.Post;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataInit {
	private final DataInit self; // 자기 자신을 프록시로 호출하여 트랜잭션을 적용하기 위해
	private final MemberFacade memberFacade;
	private final PostWriteUseCase postWriteUseCase;
	private final PostFacade postFacade;

	public DataInit(
		@Lazy DataInit self, MemberFacade memberFacade,//실제 프록시를 생성자 호출 시점이 아니라 나중에 초기화 -> 자기자신의 순환 참조 막기 위해
		PostWriteUseCase postWriteUseCase, PostFacade postFacade
	) {
		this.self = self;
		this.memberFacade = memberFacade;
		this.postWriteUseCase = postWriteUseCase;
		this.postFacade = postFacade;
	}

	@Bean
	public ApplicationRunner baseInitDataRunner() {
		return args -> {
			self.makeBaseMembers();
			self.makeBasePosts();
			self.makeBasePostComments();
		};
	}

	@Transactional
	public void makeBaseMembers() {
		if (memberFacade.count() > 0)
			return;

		memberFacade.join("system", "1234", "시스템");
		memberFacade.join("holding", "1234", "홀딩");
		memberFacade.join("admin", "1234", "관리자");
		memberFacade.join("user1", "1234", "유저1");
		memberFacade.join("user2", "1234", "유저2");
		memberFacade.join("user3", "1234", "유저3");
	}

	@Transactional
	public void makeBasePosts() {
		if (postWriteUseCase.count() > 0)
			return;

		Member user1Member = memberFacade.findByUsername("user1").orElseThrow();
		Member user2Member = memberFacade.findByUsername("user2").orElseThrow();
		Member user3Member = memberFacade.findByUsername("user3").orElseThrow();

		postWriteUseCase.write(user1Member.getId(), "제목1", "내용1");
		postWriteUseCase.write(user1Member.getId(), "제목2", "내용2");
		postWriteUseCase.write(user1Member.getId(), "제목3", "내용3");
		postWriteUseCase.write(user2Member.getId(), "제목4", "내용4");
		postWriteUseCase.write(user2Member.getId(), "제목5", "내용5");
		postWriteUseCase.write(user3Member.getId(), "제목6", "내용6");
	}

	@Transactional
	public void makeBasePostComments() {
		Post post1 = postFacade.findById(1).orElseThrow();
		Post post2 = postFacade.findById(2).orElseThrow();
		Post post3 = postFacade.findById(3).orElseThrow();
		Post post4 = postFacade.findById(4).orElseThrow();
		postFacade.findById(5).orElseThrow();
		postFacade.findById(6).orElseThrow();

		Member user1Member = memberFacade.findByUsername("user1").orElseThrow();
		Member user2Member = memberFacade.findByUsername("user2").orElseThrow();
		Member user3Member = memberFacade.findByUsername("user3").orElseThrow();

		if (post1.hasComments())
			return;

		postWriteUseCase.addComment(post1.getId(), user1Member.getId(), "댓글1");
		postWriteUseCase.addComment(post1.getId(), user2Member.getId(), "댓글2");
		postWriteUseCase.addComment(post1.getId(), user3Member.getId(), "댓글3");

		postWriteUseCase.addComment(post2.getId(), user2Member.getId(), "댓글4");
		postWriteUseCase.addComment(post2.getId(), user2Member.getId(), "댓글5");

		postWriteUseCase.addComment(post3.getId(), user3Member.getId(), "댓글6");
		postWriteUseCase.addComment(post3.getId(), user3Member.getId(), "댓글7");
		postWriteUseCase.addComment(post4.getId(), user1Member.getId(), "댓글8");
	}
}
