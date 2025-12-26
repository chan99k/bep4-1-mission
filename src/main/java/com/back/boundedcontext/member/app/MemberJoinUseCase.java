package com.back.boundedcontext.member.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.member.out.MemberRepository;
import com.back.global.exception.DomainException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberJoinUseCase {
	private final MemberRepository memberRepository;

	public Member join(String username, String password, String nickname) {
		memberRepository.findByUsername(username).ifPresent(m -> {
			throw new DomainException("409-1", "이미 존재하는 username 입니다.");
		});

		return memberRepository.save(new Member(username, password, nickname));
	}
}
