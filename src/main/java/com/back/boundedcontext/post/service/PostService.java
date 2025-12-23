package com.back.boundedcontext.post.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.back.boundedcontext.member.entity.Member;
import com.back.boundedcontext.post.entity.Post;
import com.back.boundedcontext.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;

	public long count() {
		return postRepository.count();
	}

	public Post write(Member author, String title, String content) {
		Post post = new Post(author, title, content);
		author.increaseActivityScore(3);
		return postRepository.save(post);
	}

	public Optional<Post> findById(int id) {
		return postRepository.findById(id);
	}
}
