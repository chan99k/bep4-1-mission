package com.back.boundedcontext.payout.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.payout.domain.Payout;
import com.back.boundedcontext.payout.domain.PayoutMember;
import com.back.boundedcontext.payout.out.PayoutMemberRepository;
import com.back.boundedcontext.payout.out.PayoutRepository;
import com.back.shared.payout.dto.PayoutMemberDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutCreatePayoutUseCase {
	private final PayoutMemberRepository payoutMemberRepository;
	private final PayoutRepository payoutRepository;

	public Payout createPayout(PayoutMemberDto payee) {
		PayoutMember _payee = payoutMemberRepository.getReferenceById(payee.id());
		log.debug("createPayout.payee: {}", _payee.getId());
		Payout payout = payoutRepository.save(
			new Payout(_payee)
		);

		log.debug("createPayout.payout: {}", payout.getId());

		return payout;
	}
}
