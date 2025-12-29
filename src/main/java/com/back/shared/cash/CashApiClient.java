package com.back.shared.cash;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.back.shared.cash.dto.WalletDto;

@Service
public class CashApiClient {
	private final RestClient restClient;

	public CashApiClient(@Value("${custom.global.internalBackUrl}") String internalBackUrl) {
		this.restClient = RestClient.builder()
			.baseUrl(internalBackUrl + "/api/v1/cash")
			.build();
	}

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
