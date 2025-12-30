package com.back.boundedcontext.cash.domain;

import java.time.LocalDateTime;

import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.member.domain.ReplicaMember;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CASH_MEMBER")
@Getter
@NoArgsConstructor
public class CashMember extends ReplicaMember {
	public CashMember(
		int id,
		LocalDateTime createDate, LocalDateTime modifyDate,
		String username, String password, String nickname,
		int activityScore
	) {
		super(id, createDate, modifyDate, username, password, nickname, activityScore);
	}

	public CashMemberDto toDto() {
		return new CashMemberDto(
			this.getId(), this.getCreateDate(), this.getModifyDate()
			, this.getUsername(), this.getNickname(), this.getActivityScore()
		);
	}
}
