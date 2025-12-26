package com.back.boundedcontext.cash.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.domain.Wallet;
import com.back.boundedcontext.cash.out.CashMemberRepository;
import com.back.boundedcontext.cash.out.WalletRepository;
import com.back.shared.cash.dto.CashMemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashCreateWalletUseCase {
	private final WalletRepository walletRepository;
	private final CashMemberRepository cashMemberRepository;

	public Wallet createWallet(CashMemberDto holder) {
		CashMember cashmember = cashMemberRepository.getReferenceById(holder.id());
		Wallet wallet = new Wallet(cashmember);

		return walletRepository.save(wallet);
	}
}
