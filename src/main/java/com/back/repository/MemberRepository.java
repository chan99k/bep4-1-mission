package com.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	Optional<Member> findByUsername(String username);
}
