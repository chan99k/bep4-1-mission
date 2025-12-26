package com.back.shared.member.out;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.back.global.rsdata.RsData;
import com.back.shared.member.dto.MemberBasicInfo;

@Service
public class MemberApiClient {
	private final RestClient restClient = RestClient.builder()
		.baseUrl("http://localhost:8080/member/api/v1")
		.build();

	public String getRandomSecureTip() {
		return restClient.get()
			.uri("/members/randomSecureTip")
			.retrieve()
			.body(String.class);
	}

	public MemberBasicInfo findMemberBasicInfo(int memberId) {
		RsData<MemberBasicInfo> response = restClient.get()
			.uri("/members/basic-info/{memberId}", memberId)
			.retrieve()
			.body(new ParameterizedTypeReference<>() {
			});

		if (response == null || response.data() == null) {
			throw new IllegalStateException("Member not found: " + memberId);
		}

		return response.data();
	}

}
