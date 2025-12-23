package com.back.global.initdata;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.member.app.MemberService;
import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.post.app.PostService;
import com.back.boundedcontext.post.domain.Post;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataInit {
	private final DataInit self; // 자기 자신을 프록시로 호출하여 트랜잭션을 적용하기 위해
	private final MemberService memberService;
	private final PostService postService;

	public DataInit(@Lazy DataInit self, MemberService memberService,
		PostService postService) { // 실제 프록시를 생성자 호출 시점이 아니라 나중에 초기화 -> 자기자신의 순환 참조 막기 위해
		this.self = self;
		this.memberService = memberService;
		this.postService = postService;
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
		if (memberService.count() > 0)
			return;

		memberService.join("system", "1234", "시스템");
		memberService.join("holding", "1234", "홀딩");
		memberService.join("admin", "1234", "관리자");
		memberService.join("user1", "1234", "유저1");
		memberService.join("user2", "1234", "유저2");
		memberService.join("user3", "1234", "유저3");
	}

	@Transactional
	public void makeBasePosts() {
		if (postService.count() > 0)
			return;

		Member user1Member = memberService.findByUsername("user1").orElseThrow();
		Member user2Member = memberService.findByUsername("user2").orElseThrow();
		Member user3Member = memberService.findByUsername("user3").orElseThrow();

		postService.write(user1Member, "제목1", "내용1");
		postService.write(user1Member, "제목2", "내용2");
		postService.write(user1Member, "제목3", "내용3");
		postService.write(user2Member, "제목4", "내용4");
		postService.write(user2Member, "제목5", "내용5");
		postService.write(user3Member, "제목6", "내용6");
	}

	@Transactional
	public void makeBasePostComments() {
		Post post1 = postService.findById(1).get();
		Post post2 = postService.findById(2).get();
		Post post3 = postService.findById(3).get();
		Post post4 = postService.findById(4).get();
		postService.findById(5).get();
		postService.findById(6).get();

		Member user1Member = memberService.findByUsername("user1").get();
		Member user2Member = memberService.findByUsername("user2").get();
		Member user3Member = memberService.findByUsername("user3").get();

		if (post1.hasComments())
			return;

		postService.addComment(post1.getId(), user1Member.getId(), "댓글1");
		postService.addComment(post1.getId(), user2Member.getId(), "댓글2");
		postService.addComment(post1.getId(), user3Member.getId(), "댓글3");


		postService.addComment(post2.getId(), user2Member.getId(), "댓글4");
		postService.addComment(post2.getId(), user2Member.getId(), "댓글5");

		postService.addComment(post3.getId(), user3Member.getId(), "댓글6");
		postService.addComment(post3.getId(), user3Member.getId(), "댓글7");
		postService.addComment(post4.getId(), user1Member.getId(), "댓글8");
	}
}
