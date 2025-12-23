package com.back.boundedcontext.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedcontext.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	Optional<Member> findByUsername(String username);
}
