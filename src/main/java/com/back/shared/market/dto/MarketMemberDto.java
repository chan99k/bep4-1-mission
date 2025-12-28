package com.back.shared.market.dto;

import java.time.LocalDateTime;

import com.back.boundedcontext.market.domain.MarketMember;

public record MarketMemberDto(
	int id,
	LocalDateTime createDate,
	LocalDateTime modifyDate,
	String username,
	String password,
	String nickname,
	int activityScore
) {

	public static MarketMemberDto from(MarketMember marketMember) {
		return new MarketMemberDto(
			marketMember.getId(),
			marketMember.getCreateDate(),
			marketMember.getModifyDate(),
			marketMember.getUsername(),
			marketMember.getPassword(),
			marketMember.getNickname(),
			marketMember.getActivityScore()
		);
	}

}
