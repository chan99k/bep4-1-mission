package com.back.boundedcontext.cash.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.cash.domain.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
