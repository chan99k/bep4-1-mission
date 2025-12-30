package com.back.shared.cash.dto;

import java.time.LocalDateTime;

import com.back.shared.standard.model.type.CanGetModelTypeCode;

public record CashMemberDto(
	int id,
	LocalDateTime createDate, LocalDateTime modifyDate,
	String username, String nickname,
	int activityScore
) implements CanGetModelTypeCode {
	@Override
	public String getModelTypeCode() {
		return "Cash";
	}
}
