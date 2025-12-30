package com.back.global.jpa.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.back.shared.standard.model.type.CanGetModelTypeCode;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
// 모든 엔티티들의 조상
public abstract class BaseEntity implements CanGetModelTypeCode {
	@Transient
	private final List<Object> domainEvents = new ArrayList<>();
	@Override
	public String getModelTypeCode() {
		return this.getClass().getSimpleName();
	}

	public abstract int getId();

	public abstract LocalDateTime getCreateDate();

	public abstract LocalDateTime getModifyDate();

	// 하위 엔티티에서 이벤트를 등록할 때 사용
	protected void registerEvent(Object event) {
		this.domainEvents.add(event);
	}

	// Spring Data JPA가 save() 시점에 호출하여 이벤트를 수집
	@DomainEvents
	protected Collection<Object> domainEvents() {
		return Collections.unmodifiableList(domainEvents);
	}

	// 이벤트 발행이 완료된 후 리스트를 비움
	@AfterDomainEventPublication
	protected void clearDomainEvents() {
		this.domainEvents.clear();
	}
}
