package com.back.boundedcontext.market.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "custom.market.product")
public record MarketPolicyProperties(
	double payoutRate
) {
}
