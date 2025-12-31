package com.back.boundedcontext.payout.app;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.back.boundedcontext.payout.domain.Payout;
import com.back.boundedcontext.payout.out.PayoutRepository;
import com.back.global.rsdata.RsData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayoutCompletePayoutsMoreUseCase {
	private final PayoutRepository payoutRepository;

	public RsData<Integer> completePayoutsMore(int limit) {
		List<Payout> activePayouts = findActivePayouts(limit);

		if (activePayouts.isEmpty())
			return new RsData<>("200-1", "더 이상 정산할 정산내역이 없습니다.", 0);

		activePayouts.forEach(payout -> {
			payout.completePayout();
			payoutRepository.save(payout);  // 도메인 이벤트 발행을 위해 명시적으로 save() 호출
		});

		return new RsData<>(
			"201-1",
			"%d건의 정산이 처리되었습니다.".formatted(activePayouts.size()),
			activePayouts.size()
		);
	}

	private List<Payout> findActivePayouts(int limit) {
		return payoutRepository.findByPayoutDateIsNullAndAmountGreaterThanOrderByIdAsc(0, PageRequest.of(0, limit));
	}

}
