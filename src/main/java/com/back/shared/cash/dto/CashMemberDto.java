package com.back.shared.cash.dto;

import java.time.LocalDateTime;

import com.back.boundedcontext.cash.domain.CashMember;

public record CashMemberDto(
	int id,
	LocalDateTime createDate, LocalDateTime modifyDate,
	String username, String nickname,
	int activityScore
) {
	public CashMemberDto(CashMember member) {
		this(
			member.getId(),
			member.getCreateDate(),
			member.getModifyDate(),
			member.getUsername(),
			member.getNickname(),
			member.getActivityScore()
		);
	}
}
