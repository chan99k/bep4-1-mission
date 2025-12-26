package com.back.shared.member.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class ReplicaMember extends BaseMember {
	@Id
	private int id;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;

	protected ReplicaMember(
		int id,
		LocalDateTime createDate, LocalDateTime modifyDate,
		String username, String password, String nickname
	) {
		super(username, password, nickname);
		this.id = id;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
	}
}
