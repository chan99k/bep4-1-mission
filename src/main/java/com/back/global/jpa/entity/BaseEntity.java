package com.back.global.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
// 모든 엔티티들의 조상
public abstract class BaseEntity {
	public String getModelTypeCode() {
		return this.getClass().getSimpleName();
	}

	public abstract int getId();

	public abstract LocalDateTime getCreateDate();

	public abstract LocalDateTime getModifyDate();
}
