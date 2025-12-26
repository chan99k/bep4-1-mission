package com.back.boundedcontext.member.app;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.member.out.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberSupport {
	private final MemberRepository memberRepository;

	public long count() {
		return memberRepository.count();
	}

	@Transactional(readOnly = true)
	public Optional<Member> findByUsername(String username) {
		return memberRepository.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public Optional<Member> findById(int id) {
		return memberRepository.findById(id);
	}


}
