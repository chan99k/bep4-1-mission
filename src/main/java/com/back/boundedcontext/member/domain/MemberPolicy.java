package com.back.boundedcontext.member.domain;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class MemberPolicy {
	private static final int PASSWORD_CHANGE_DAYS = 90;

	/**
	 * 비밀번호 재설정 주기를 Duration 형식으로 반환
	 */
	public Duration getNeedToChangePasswordPeriod() {
		return Duration.ofDays(PASSWORD_CHANGE_DAYS);
	}

	public int getNeedToChangePasswordDays() {
		return PASSWORD_CHANGE_DAYS;
	}

	/**
	 * @param lastChangedAt 비밀번호가 교체된 날짜
	 * @return 정책상 비밀번호를 교체해야 하는지 여부
	 */
	public boolean isNeedToChangePassword(LocalDateTime lastChangedAt) {
		if (lastChangedAt == null)
			return true;

		return lastChangedAt.plusDays(PASSWORD_CHANGE_DAYS)
			.isBefore(LocalDateTime.now());
	}
}
