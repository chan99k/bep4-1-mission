package com.back.shared.cash.event;

import com.back.shared.market.dto.OrderDto;
import com.back.shared.standard.result.type.ResultType;

public record CashOrderPaymentFailed(
	String resultCode,
	String message,
	OrderDto order,
	long pgPaymentAmount,
	long l1
) implements ResultType {

}
