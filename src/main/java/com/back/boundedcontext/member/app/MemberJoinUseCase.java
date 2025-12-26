package com.back.boundedcontext.member.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.member.out.MemberRepository;
import com.back.global.exception.DomainException;
import com.back.global.rsdata.RsData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberJoinUseCase {
	private final MemberRepository memberRepository;

	public RsData<Member> join(String username, String password, String nickname) {
		memberRepository.findByUsername(username).ifPresent(m -> {
			throw new DomainException("409-1", "이미 존재하는 username 입니다.");
		});

		Member saved = memberRepository.save(new Member(username, password, nickname));

		return new RsData<>("201-1", "%d번 회원이 생성되었습니다".formatted(saved.getId()), saved);
	}
}
