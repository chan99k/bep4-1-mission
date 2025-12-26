package com.back.boundedcontext.member.in;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.boundedcontext.member.app.MemberFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {
	private final MemberFacade memberFacade;

	@GetMapping("randomSecureTip")
	@Transactional(readOnly = true)
	public String getRandomSecureTip() {
		return memberFacade.getRandomSecureTip();
	}
}
