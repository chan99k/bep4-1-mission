package com.back.boundedcontext.post.app;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.domain.PostMember;
import com.back.global.rsdata.RsData;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.post.dto.PostDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostFacade {

	private final PostWriteUseCase postWriteUseCase;
	private final PostMemberSyncUseCase postMemberSyncUseCase;
	private final PostSupport postSupport;

	@Transactional(readOnly = true)
	public long count() {
		return postSupport.count();
	}

	@Transactional
	public RsData<Post> write(PostMember author, String title, String content) {
		return postWriteUseCase.write(author, title, content);
	}

	@Transactional(readOnly = true)
	public Optional<Post> findById(int id) {
		return postSupport.findById(id);
	}

	@Transactional
	public PostMember syncMember(MemberDto memberDto) {
		return postMemberSyncUseCase.syncMember(memberDto);
	}

	@Transactional(readOnly = true)
	public Optional<PostMember> findMemberByUsername(String username) {
		return postSupport.findMemberByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<PostDto> findByOrderByIdDesc() {
		return postSupport.findAllByOrderByIdDesc().stream().map(PostDto::from).toList();
	}
}
