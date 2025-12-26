package com.back.boundedcontext.post.domain;

import java.time.LocalDateTime;

import com.back.shared.member.domain.ReplicaMember;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST_MEMBER")
@Getter
@NoArgsConstructor
public class PostMember extends ReplicaMember {
	public PostMember(
		int id,
		LocalDateTime createDate, LocalDateTime modifyDate,
		String username, String password, String nickname,
		int activityScore
	) {
		super(id, createDate, modifyDate, username, password, nickname, activityScore);
	}
}
