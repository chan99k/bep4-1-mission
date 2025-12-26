package com.back.boundedcontext.cash.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.cash.domain.CashMember;

public interface CashMemberRepository extends JpaRepository<CashMember, Long> {
	Optional<CashMember> findByUsername(String username);
}
