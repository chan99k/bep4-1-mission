package com.back.boundedcontext.cash.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.domain.Wallet;
import com.back.boundedcontext.cash.out.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashCreateWalletUseCase {
	private final WalletRepository walletRepository;

	public Wallet createWallet(CashMember holder) {
		Wallet wallet = new Wallet(holder);

		return walletRepository.save(wallet);
	}
}
