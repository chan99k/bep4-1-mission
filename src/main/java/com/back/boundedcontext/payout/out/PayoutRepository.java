package com.back.boundedcontext.payout.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.payout.domain.Payout;

public interface PayoutRepository extends JpaRepository<Payout, Integer> {
}
