package com.back.boundedcontext.member.in;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.member.app.MemberFacade;
import com.back.boundedcontext.member.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MemberDataInit {
	private final MemberDataInit self; // 자기 자신을 프록시로 호출하여 트랜잭션을 적용하기 위해
	private final MemberFacade memberFacade;

	public MemberDataInit(
		@Lazy MemberDataInit self, //실제 프록시를 생성자 호출 시점이 아니라 나중에 초기화 -> 자기자신의 순환 참조 막기 위해
		MemberFacade memberFacade
	) {
		this.self = self;
		this.memberFacade = memberFacade;
	}

	@Bean
	@Order(1)
	public ApplicationRunner memberDataInitApplicationRunner() {
		return args -> {
			self.makeBaseMembers();
		};
	}

	@Transactional
	public void makeBaseMembers() {
		if (memberFacade.count() > 0)
			return;

		Member systemMember = memberFacade.join("system", "1234", "시스템").data();
		Member holdingMember = memberFacade.join("holding", "1234", "홀딩").data();
		Member adminMember = memberFacade.join("admin", "1234", "관리자").data();
		Member user1Member = memberFacade.join("user1", "1234", "유저1").data();
		Member user2Member = memberFacade.join("user2", "1234", "유저2").data();
		Member user3Member = memberFacade.join("user3", "1234", "유저3").data();
	}
}
