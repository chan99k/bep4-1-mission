package com.back.boundedcontext.payout.app;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.payout.domain.PayoutCandidateItem;
import com.back.boundedcontext.payout.domain.PayoutMember;
import com.back.boundedcontext.payout.out.PayoutCandidateItemRepository;
import com.back.boundedcontext.payout.out.PayoutMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayoutSupport {
	private final PayoutMemberRepository payoutMemberRepository;
	private final PayoutCandidateItemRepository payoutCandidateItemRepository;

	public Optional<PayoutMember> findHolingMember() {
		return payoutMemberRepository.findByUsername("holding");
	}

	public Optional<PayoutMember> findMemberById(int id) {
		return payoutMemberRepository.findById(id);
	}

	public Optional<PayoutMember> findSystemMember() {
		return payoutMemberRepository.findByUsername("system");
	}

	public List<PayoutCandidateItem> findPayoutCandidateItems() {
		return payoutCandidateItemRepository.findAll();
	}
}
