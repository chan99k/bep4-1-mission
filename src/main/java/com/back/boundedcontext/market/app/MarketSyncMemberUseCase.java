package com.back.boundedcontext.market.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.out.MarketMemberRepository;
import com.back.global.eventpublisher.EventPublisher;
import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.market.event.MarketMemberCreated;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketSyncMemberUseCase {
	private final MarketMemberRepository marketMemberRepository;
	private final EventPublisher eventPublisher;

	public MarketMember syncMember(MemberDto member) {
		boolean isNew = !marketMemberRepository.existsById(member.id());

		MarketMember buyer = marketMemberRepository.save(
			new MarketMember(member.id(), member.createDate(), member.modifyDate(), member.username(), "",
				member.nickname(), member.activityScore()));

		if (isNew) {
			eventPublisher.publishEvent(new MarketMemberCreated(MarketMemberDto.from(buyer)));
		}

		return buyer;
	}
}
