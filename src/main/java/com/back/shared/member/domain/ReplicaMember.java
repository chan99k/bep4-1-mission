package com.back.shared.member.domain;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter(value = PROTECTED)
@NoArgsConstructor
public abstract class ReplicaMember extends BaseMember {
	@Id
	private int id;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;

	protected ReplicaMember(String username, String password, String nickname) {
		super(username, password, nickname);
	}
}
