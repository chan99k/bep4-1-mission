package com.back.boundedcontext.market.domain;

import java.time.LocalDateTime;

import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.member.domain.ReplicaMember;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MARKET_MEMBER")
@Getter
@NoArgsConstructor
public class MarketMember extends ReplicaMember {
	public MarketMember(int id, LocalDateTime createDate, LocalDateTime modifyDate, String username, String password,
		String nickname, int activityScore) {
		super(id, createDate, modifyDate, username, password, nickname, activityScore);
	}

	public MarketMemberDto toDto() {
		return new MarketMemberDto(
			this.getId(),
			this.getCreateDate(),
			this.getModifyDate(),
			this.getUsername(),
			this.getPassword(),
			this.getNickname(),
			this.getActivityScore()
		);
	}
}
