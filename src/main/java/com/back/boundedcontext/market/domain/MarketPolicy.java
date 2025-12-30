package com.back.boundedcontext.market.domain;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.market.config.MarketPolicyProperties;

import lombok.Getter;

@Service
@Getter
public class MarketPolicy {

	private final double payoutRate;

	public MarketPolicy(MarketPolicyProperties props) {
		this.payoutRate = props.payoutRate();
	}

	public double payoutRate() {
		return payoutRate;
	}

	public long calculateSalePriceWithoutFee(long salePrice, double payoutRate) {
		return Math.round(salePrice * payoutRate / 100);
	}

	public long calculatePayoutFee(long salePrice, double payoutRate) {
		return salePrice - calculateSalePriceWithoutFee(salePrice, payoutRate);
	}
}
