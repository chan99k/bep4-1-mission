package com.back.boundedcontext.member.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.member.domain.MemberPolicy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberGetRandomSecureTipUseCase {
	private final MemberPolicy memberPolicy;

	/**
	 * @return 랜덤한 사용자 계정 보안 팁을 반환하는 메서드. 현재는 그냥 고정 메시지를 출력한다.
	 */
	public String getRandomSecureTip() {
		return "비밀번호의 유효기간은 %d일 입니다."
			.formatted(memberPolicy.getNeedToChangePasswordDays());
	}
}
