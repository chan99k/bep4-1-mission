package com.back.boundedcontext.cash.app;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.domain.CashPolicy;
import com.back.boundedcontext.cash.domain.Wallet;
import com.back.boundedcontext.cash.out.CashMemberRepository;
import com.back.boundedcontext.cash.out.WalletRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CashSupport {
	private final CashMemberRepository cashMemberRepository;
	private final WalletRepository walletRepository;

	public Optional<CashMember> findMemberByUsername(String username) {
		return cashMemberRepository.findByUsername(username);
	}

	public Optional<Wallet> findWalletByHolder(CashMember holder) {
		return walletRepository.findByHolder(holder);
	}

	public Optional<Wallet> findWalletByHolderId(int holderId) {
		return walletRepository.findByHolderId(holderId);
	}

	public Optional<Wallet> findHoldingWallet() {
		return walletRepository.findByHolderId(CashPolicy.HOLDING_MEMBER_ID);
	}
}
