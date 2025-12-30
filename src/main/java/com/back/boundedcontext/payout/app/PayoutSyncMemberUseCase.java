package com.back.boundedcontext.payout.app;

import org.springframework.stereotype.Component;

import com.back.boundedcontext.payout.domain.PayoutMember;
import com.back.boundedcontext.payout.out.PayoutMemberRepository;
import com.back.global.eventpublisher.EventPublisher;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.payout.event.PayoutMemberCreated;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PayoutSyncMemberUseCase {
	private final PayoutMemberRepository payoutMemberRepository;
	private final EventPublisher eventPublisher;

	public PayoutMember syncMember(MemberDto memberDto) {
		boolean isNew = !payoutMemberRepository.existsById(memberDto.id());

		PayoutMember member = payoutMemberRepository.save(
			new PayoutMember(
				memberDto.id(),
				memberDto.createDate(),
				memberDto.modifyDate(),
				memberDto.username(),
				"",
				memberDto.nickname(),
				memberDto.activityScore()
			)
		);

		if (isNew) {
			eventPublisher.publishEvent(
				new PayoutMemberCreated(
					member.toDto()
				)
			);
		}

		return member;
	}
}
