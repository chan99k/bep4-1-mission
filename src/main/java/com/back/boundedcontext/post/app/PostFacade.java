package com.back.boundedcontext.post.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.domain.PostMember;
import com.back.boundedcontext.post.out.PostMemberRepository;
import com.back.boundedcontext.post.out.PostRepository;
import com.back.global.rsdata.RsData;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostFacade {
	private final PostRepository postRepository;
	private final PostWriteUseCase postWriteUseCase;
	private final PostMemberRepository postMemberRepository;

	@Transactional(readOnly = true)
	public long count() {
		return postRepository.count();
	}

	@Transactional
	public RsData<Post> write(int authorId, String title, String content) {
		return postWriteUseCase.write(authorId, title, content);
	}

	@Transactional(readOnly = true)
	public Optional<Post> findById(int id) {
		return postRepository.findById(id);
	}

	public PostMember syncMember(MemberDto member) {
		PostMember postMember = new PostMember(
			member.id(),
			member.createDate(),
			member.modifyDate(),
			member.username(),
			"",
			member.nickname()
		);

		postMember.setId(member.id());
		postMember.setCreateDate(member.createDate());
		postMember.setModifyDate(member.modifyDate());

		return postMemberRepository.save(postMember);
	}
}
