package com.back.boundedcontext.post.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.post.domain.PostMember;

public interface PostMemberRepository extends JpaRepository<PostMember, Long> {
	Optional<PostMember> findByUsername(String username);
}
