package com.back.boundedcontext.cash.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.out.CashMemberRepository;
import com.back.global.eventpublisher.EventPublisher;
import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.cash.event.CashMemberCreated;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashSyncMemberUseCase {
	private final CashMemberRepository cashMemberRepository;
	private final EventPublisher eventPublisher;

	public CashMember syncMember(MemberDto member) {
		boolean isNew = !cashMemberRepository.existsById(member.id());

		CashMember cashMember = cashMemberRepository.save(
			new CashMember(
				member.id(),
				member.createDate(),
				member.modifyDate(),
				member.username(),
				"",
				member.nickname(),
				member.activityScore()
			)
		);

		if (isNew) {
			eventPublisher.publishEvent(
				new CashMemberCreated(
					new CashMemberDto(cashMember)
				)
			);
		}

		return cashMember;
	}
}
