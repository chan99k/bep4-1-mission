package com.back.shared.cash.dto;

import java.time.LocalDateTime;

import com.back.boundedcontext.cash.domain.Wallet;

public record WalletDto(
	int id,
	int holderId,
	String holderName,
	long balance,
	LocalDateTime createDate,
	LocalDateTime modifyDate
) {
	public WalletDto(Wallet wallet) {
		this(
			wallet.getId(), wallet.getHolder().getId(), wallet.getHolder().getUsername(), wallet.getBalance(),
			wallet.getCreateDate(), wallet.getModifyDate()
		);
	}
}
