package com.back.global.jpa.entity;

import static jakarta.persistence.GenerationType.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseIdAndTime extends BaseEntity {
	@Transient
	private final List<Object> domainEvents = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;

	@CreatedDate
	private LocalDateTime createDate;

	@LastModifiedDate
	private LocalDateTime modifyDate;

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
