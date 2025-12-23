package com.back.boundedcontext.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
