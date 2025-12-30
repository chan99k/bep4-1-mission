package com.back.boundedcontext.payout.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.payout.domain.PayoutMember;

public interface PayoutMemberRepository extends JpaRepository<PayoutMember, Integer> {
	Optional<PayoutMember> findByUsername(String username);
}
