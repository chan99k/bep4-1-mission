package com.back.shared.cash.dto;

import java.time.LocalDateTime;

import com.back.shared.standard.model.type.CanGetModelTypeCode;

public record WalletDto(
	int id,
	int holderId,
	String holderName,
	long balance,
	LocalDateTime createDate,
	LocalDateTime modifyDate
) implements CanGetModelTypeCode {
	@Override
	public String getModelTypeCode() {
		return "Wallet";
	}
}
