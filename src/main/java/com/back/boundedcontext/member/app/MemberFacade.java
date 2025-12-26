package com.back.boundedcontext.member.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.member.out.MemberRepository;
import com.back.global.rsdata.RsData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFacade {
	private final MemberRepository memberRepository;
	private final MemberJoinUseCase memberJoinUseCase;

	@Transactional(readOnly = true)
	public long count() {
		return memberRepository.count();
	}

	@Transactional
	public RsData<Member> join(String username, String password, String nickname) {
		return memberJoinUseCase.join(username, password, nickname);
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
