package com.back.boundedcontext.market.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.out.MarketMemberRepository;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketSyncMemberUseCase {
	private final MarketMemberRepository marketMemberRepository;

	public MarketMember syncMember(MemberDto member) {
		MarketMember marketMember = new MarketMember(
			member.id(),
			member.createDate(),
			member.modifyDate(),
			member.username(),
			"",
			member.nickname(),
			member.activityScore()
		);

		return marketMemberRepository.save(marketMember);
	}
}
