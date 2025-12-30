package com.back.shared.payout.dto;

import java.time.LocalDateTime;

import com.back.shared.standard.model.type.CanGetModelTypeCode;

public record PayoutDto(
	int id,
	LocalDateTime createDate,
	LocalDateTime modifyDate,
	int payeeId,
	String payeeName,
	LocalDateTime payoutDate,
	long amount, boolean isPayeeSystem
) implements CanGetModelTypeCode {

	@Override
	public String getModelTypeCode() {
		return "Payout";
	}
}
