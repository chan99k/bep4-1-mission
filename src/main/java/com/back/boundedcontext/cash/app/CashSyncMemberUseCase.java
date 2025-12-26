package com.back.boundedcontext.cash.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.out.CashMemberRepository;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashSyncMemberUseCase {
	private final CashMemberRepository cashMemberRepository;

	public CashMember syncMember(MemberDto member) {
		CashMember cashMember = new CashMember(
			member.id(),
			member.createDate(),
			member.modifyDate(),
			member.username(),
			"",
			member.nickname(),
			member.activityScore()
		);

		return cashMemberRepository.save(cashMember);
	}
}
