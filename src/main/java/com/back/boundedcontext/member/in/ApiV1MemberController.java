package com.back.boundedcontext.member.in;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.boundedcontext.member.app.MemberFacade;
import com.back.boundedcontext.member.domain.Member;
import com.back.global.rsdata.RsData;
import com.back.shared.member.dto.MemberBasicInfo;

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

	@GetMapping("/basic-info/{memberId}")
	public RsData<MemberBasicInfo> findMemberBasicInfo(@PathVariable int memberId) {
		Optional<Member> foundMember = memberFacade.findById(memberId);
		if (foundMember.isPresent()) {
			Member member = foundMember.get();
			MemberBasicInfo memberBasicInfo = new MemberBasicInfo(member.getId(), member.getUsername());
			return new RsData<>("200-2", "멤버 아이디와 닉네임", memberBasicInfo);
		}

		return new RsData<>("404-1", "멤버 데이터 찾을 수 없음");
	}
}
