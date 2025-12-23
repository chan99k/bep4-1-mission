package com.back.global.jpa.entity;

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
public class BaseEntity {
	public String getModelTypeCode() {
		return this.getClass().getSimpleName();
	}
}
