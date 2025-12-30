package com.back.shared.market.dto;

import java.time.LocalDateTime;

import com.back.shared.standard.model.type.CanGetModelTypeCode;

public record MarketMemberDto(
	int id,
	LocalDateTime createDate,
	LocalDateTime modifyDate,
	String username,
	String password,
	String nickname,
	int activityScore
) implements CanGetModelTypeCode {

	@Override
	public String getModelTypeCode() {
		return "MarketMember";
	}
}
