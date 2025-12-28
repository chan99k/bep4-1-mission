package com.back.boundedcontext.post.app;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.domain.PostMember;
import com.back.boundedcontext.post.out.PostMemberRepository;
import com.back.boundedcontext.post.out.PostRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostSupport {
	private final PostRepository postRepository;
	private final PostMemberRepository postMemberRepository;

	public Optional<PostMember> findMemberByUsername(String username) {
		return postMemberRepository.findByUsername(username);
	}

	public long count() {
		return postRepository.count();
	}

	public Optional<Post> findById(int id) {
		return postRepository.findById(id);
	}

	public List<Post> findAll() {
		return postRepository.findAll();
	}

	public List<Post> findAllByOrderByIdDesc() {
		return postRepository.findByOrderByIdDesc();
	}
}
