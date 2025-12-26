package com.back.boundedcontext.post.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.post.domain.PostMember;

public interface PostMemberRepository extends JpaRepository<PostMember, Long> {
}
