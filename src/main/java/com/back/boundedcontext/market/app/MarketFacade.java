package com.back.boundedcontext.market.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.market.domain.MarketMember;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketFacade {
	private final MarketSyncMemberUseCase marketSyncMemberUseCase;

	@Transactional
	public MarketMember syncMember(MemberDto member) {
		return marketSyncMemberUseCase.syncMember(member);
	}
}
