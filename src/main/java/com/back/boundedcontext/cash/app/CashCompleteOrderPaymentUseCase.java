package com.back.boundedcontext.cash.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.cash.domain.CashLog;
import com.back.boundedcontext.cash.domain.Wallet;
import com.back.global.eventpublisher.EventPublisher;
import com.back.shared.cash.event.CashOrderPaymentFailed;
import com.back.shared.cash.event.CashOrderPaymentSucceeded;
import com.back.shared.market.dto.OrderDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashCompleteOrderPaymentUseCase {
	private final CashSupport cashSupport;
	private final EventPublisher eventPublisher;

	public void completeOrderPayment(OrderDto order, long pgPaymentAmount) {
		Wallet customerWallet = cashSupport.findWalletByHolderId(order.customerId()).get();
		Wallet holdingWallet = cashSupport.findHoldingWallet().get();

		if (pgPaymentAmount > 0) {
			customerWallet.credit(
				pgPaymentAmount,
				CashLog.EventType.충전__PG결제_토스페이먼츠,
				"Order",
				order.id()
			);
		}

		boolean canPay = customerWallet.getBalance() >= order.salePrice();

		if (canPay) {
			customerWallet.debit(
				order.salePrice(),
				CashLog.EventType.사용__주문결제,
				"Order",
				order.id()
			);

			holdingWallet.credit(
				order.salePrice(),
				CashLog.EventType.임시보관__주문결제,
				"Order",
				order.id()
			);

			eventPublisher.publishEvent(
				new CashOrderPaymentSucceeded(
					order,
					pgPaymentAmount
				)
			);
		} else {
			eventPublisher.publishEvent(
				new CashOrderPaymentFailed(
					"400-1",
					"충전은 완료했지만 %번 주문을 결제완료처리를 하기에는 예치금이 부족합니다.".formatted(order.id()),
					order,
					pgPaymentAmount,
					pgPaymentAmount - customerWallet.getBalance()
				)
			);
		}
	}
}
