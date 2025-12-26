package com.back.boundedcontext.post.app;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.post.domain.PostMember;
import com.back.boundedcontext.post.out.PostMemberRepository;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostMemberSyncUseCase {
	private final PostMemberRepository postMemberRepository;

	public PostMember syncMember(MemberDto memberDto) {
		PostMember postMember = new PostMember(
			memberDto.id(),
			memberDto.createDate(),
			memberDto.modifyDate(),
			memberDto.username(),
			"",
			memberDto.nickname(),
			memberDto.activityScore()
		);

		postMember.setId(memberDto.id());
		postMember.setCreateDate(memberDto.createDate());
		postMember.setModifyDate(memberDto.modifyDate());

		return postMemberRepository.save(postMember);
	}
}
