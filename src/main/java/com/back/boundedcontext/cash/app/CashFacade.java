package com.back.boundedcontext.cash.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.domain.Wallet;
import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.payout.dto.PayoutDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashFacade {
	private final CashCreateWalletUseCase cashCreateWalletUseCase;
	private final CashSyncMemberUseCase cashSyncMemberUseCase;
	private final CashCompleteOrderPaymentUseCase cashCompleteOrderPaymentUseCase;
	private final CashCompletePayoutUseCase cashCompletePayoutUseCase;
	private final CashSupport cashSupport;

	@Transactional
	public CashMember syncMember(MemberDto member) {
		return cashSyncMemberUseCase.syncMember(member);
	}

	@Transactional
	public Wallet createWallet(CashMemberDto holder) {
		return cashCreateWalletUseCase.createWallet(holder);
	}

	@Transactional(readOnly = true)
	public Optional<CashMember> findMemberByUsername(String username) {
		return cashSupport.findMemberByUsername(username);
	}

	@Transactional(readOnly = true)
	public Optional<Wallet> findWalletByHolder(CashMember holder) {
		return cashSupport.findWalletByHolder(holder);
	}

	@Transactional
	public void completeOrderPayment(OrderDto orderDto, long pgPaymentAmount) {
		cashCompleteOrderPaymentUseCase.completeOrderPayment(orderDto, pgPaymentAmount);
	}

	@Transactional(readOnly = true)
	public Optional<Wallet> findWalletByHolderId(int holderId) {
		return cashSupport.findWalletByHolderId(holderId);
	}

	@Transactional
	public void completePayout(PayoutDto payout) {
		cashCompletePayoutUseCase.completePayout(payout);
	}
}
