package com.back.initdata;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import com.back.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataInit {
	private final DataInit self; // 자기 자신을 프록시로 호출하여 트랜잭션을 적용하기 위해
	private final MemberService memberService;

	public DataInit(@Lazy DataInit self, MemberService memberService) { // 실제 프록시를 생성자 호출 시점이 아니라 나중에 초기화 -> 자기자신의 순환 참조 막기 위해
		this.self = self;
		this.memberService = memberService;
	}

	@Bean
	public ApplicationRunner baseInitDataRunner() {
		return args -> {
			self.makeBaseMembers();
		};
	}

	@Transactional
	public void makeBaseMembers() {
		if (memberService.count() > 0) return;

		memberService.join("system", "1234", "시스템");
		memberService.join("holding", "1234", "홀딩");
		memberService.join("admin", "1234", "관리자");
		memberService.join("user1", "1234", "유저1");
		memberService.join("user2", "1234", "유저2");
		memberService.join("user3", "1234", "유저3");
	}
}
