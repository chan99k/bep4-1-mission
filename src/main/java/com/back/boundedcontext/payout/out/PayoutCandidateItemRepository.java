package com.back.boundedcontext.payout.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.payout.domain.PayoutCandidateItem;

public interface PayoutCandidateItemRepository extends JpaRepository<PayoutCandidateItem, Integer> {
}
