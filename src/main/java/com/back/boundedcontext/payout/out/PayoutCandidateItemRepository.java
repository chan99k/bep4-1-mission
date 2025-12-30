package com.back.boundedcontext.payout.out;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.payout.domain.PayoutCandidateItem;

public interface PayoutCandidateItemRepository extends JpaRepository<PayoutCandidateItem, Integer> {
	List<PayoutCandidateItem> findByPayoutItemIsNullAndPaymentDateBeforeOrderByPayeeAscIdAsc(LocalDateTime paymentDate, Pageable pageable);
}
