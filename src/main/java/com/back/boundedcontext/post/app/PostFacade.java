package com.back.boundedcontext.post.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.out.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostFacade {
	private final PostRepository postRepository;
	private final PostWriteUseCase postWriteUseCase;

	@Transactional(readOnly = true)
	public long count() {
		return postRepository.count();
	}

	@Transactional
	public Post write(Member author, String title, String content) {
		return postWriteUseCase.write(author, title, content);
	}

	@Transactional(readOnly = true)
	public Optional<Post> findById(int id) {
		return postRepository.findById(id);
	}
}
