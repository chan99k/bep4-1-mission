package com.back.shared.member.domain;

import static jakarta.persistence.GenerationType.*;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public abstract class SourceMember extends BaseMember {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	@CreatedDate
	private LocalDateTime createDate;
	@LastModifiedDate
	private LocalDateTime modifyDate;

	protected SourceMember(String username, String password, String nickname) {
		super(username, password, nickname);
	}
}
