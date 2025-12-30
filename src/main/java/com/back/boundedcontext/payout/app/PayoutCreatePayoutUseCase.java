package com.back.boundedcontext.payout.app;

import org.springframework.stereotype.Service;

import com.back.shared.payout.dto.PayoutMemberDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PayoutCreatePayoutUseCase {
	public void createPayout(PayoutMemberDto payee) {
		log.debug("createPayout.payee: {}", payee.id());
	}
}
