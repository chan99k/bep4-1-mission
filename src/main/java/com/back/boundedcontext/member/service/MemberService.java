package com.back.boundedcontext.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.member.entity.Member;
import com.back.boundedcontext.member.repository.MemberRepository;
import com.back.global.exception.DomainException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public long count() {
		return memberRepository.count();
	}

	public void join(String username, String password, String nickname) {
		findByUsername(username).ifPresent(m -> {
			throw new DomainException("409-1", "이미 존재하는 username 입니다.");
		});

		memberRepository.save(new Member(username, password, nickname));
	}

	public Optional<Member> findByUsername(String username) {
		return memberRepository.findByUsername(username);
	}

	public Optional<Member> findById(int id) {
		return memberRepository.findById(id);
	}
}
