package com.back.boundedcontext.member.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.member.domain.Member;
import com.back.global.rsdata.RsData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFacade {
	private final MemberSupport memberSupport;
	private final MemberJoinUseCase memberJoinUseCase;
	private final MemberGetRandomSecureTipUseCase memberGetRandomSecureTipUseCase;

	@Transactional(readOnly = true)
	public long count() {
		return memberSupport.count();
	}

	@Transactional
	public RsData<Member> join(String username, String password, String nickname) {
		return memberJoinUseCase.join(username, password, nickname);
	}

	@Transactional(readOnly = true)
	public Optional<Member> findByUsername(String username) {
		return memberSupport.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public Optional<Member> findById(int id) {
		return memberSupport.findById(id);
	}

	/**
	 * @return 랜덤한 사용자 계정 보안 팁을 반환하는 메서드. 현재는 그냥 고정 메시지를 출력한다.
	 */
	public String getRandomSecureTip() {
		return memberGetRandomSecureTipUseCase.getRandomSecureTip();
	}
}
