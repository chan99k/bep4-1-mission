package com.back.boundedcontext.payout.app;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.payout.domain.PayoutCandidateItem;
import com.back.global.rsdata.RsData;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayoutFacade {
	private final PayoutSyncMemberUseCase payoutSyncMemberUseCase;
	private final PayoutCreatePayoutUseCase payoutCreatePayoutUseCase;
	private final PayoutAddPayoutCandidateItemsUseCase payoutAddPayoutCandidateItemsUseCase;
	private final PayoutCollectPayoutItemsMoreUseCase payoutCollectPayoutItemsMoreUseCase;
	private final PayoutCompletePayoutsMoreUseCase payoutCompletePayoutsMoreUseCase;
	private final PayoutSupport payoutSupport;

	@Transactional
	public void syncMember(MemberDto member) {
		payoutSyncMemberUseCase.syncMember(member);
	}

	@Transactional
	public void createPayout(int payeeId) {
		payoutCreatePayoutUseCase.createPayout(payeeId);
	}

	@Transactional
	public void addPayoutCandidateItems(OrderDto order) {
		payoutAddPayoutCandidateItemsUseCase.addPayoutCandidateItems(order);
	}

	@Transactional
	public RsData<Integer> collectPayoutItemsMore(int limit) {
		return payoutCollectPayoutItemsMoreUseCase.collectPayoutItemsMore(limit);
	}

	@Transactional(readOnly = true)
	public List<PayoutCandidateItem> findPayoutCandidateItems() {
		return payoutSupport
			.findPayoutCandidateItems();
	}

	@Transactional
	public RsData<Integer> completePayoutsMore(int limit) {
		return payoutCompletePayoutsMoreUseCase.completePayoutsMore(limit);
	}
}
