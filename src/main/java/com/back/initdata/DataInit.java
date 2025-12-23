package com.back.initdata;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import com.back.entity.Member;
import com.back.service.MemberService;
import com.back.service.PostService;

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
}
