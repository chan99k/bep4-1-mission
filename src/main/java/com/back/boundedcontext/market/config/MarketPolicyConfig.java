package com.back.boundedcontext.market.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(MarketPolicyProperties.class)
@Configuration
class MarketPolicyConfig {}
