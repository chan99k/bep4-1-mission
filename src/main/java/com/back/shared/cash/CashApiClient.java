package com.back.shared.cash;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.back.shared.cash.dto.WalletDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashApiClient {
	private final RestClient restClient = RestClient.builder()
		.baseUrl("http://localhost:8080/api/v1/cash")
		.build();

	public WalletDto getWalletByHolderId(int holderId) {
		return restClient.get().uri("/wallets/by-holder/{holderId}", holderId)
			.retrieve()
			.body(new ParameterizedTypeReference<>() {
			});
	}

	public long getBalanceByHolderId(int holderId) {
		WalletDto walletByHolderId = getWalletByHolderId(holderId);
		return walletByHolderId.balance();
	}
}
