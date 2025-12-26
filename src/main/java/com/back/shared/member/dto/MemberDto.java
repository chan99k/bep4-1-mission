package com.back.shared.member.dto;

import java.time.LocalDateTime;

import com.back.boundedcontext.member.domain.Member;

public record MemberDto(int id, LocalDateTime createDate, LocalDateTime modifyDate, String username, String nickname) {
	public MemberDto(Member member) {
		this(
			member.getId(),
			member.getCreateDate(),
			member.getModifyDate(),
			member.getUsername(),
			member.getNickname()
		);
	}
}
