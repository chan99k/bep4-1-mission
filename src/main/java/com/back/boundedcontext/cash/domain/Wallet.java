package com.back.boundedcontext.cash.domain;

import static jakarta.persistence.CascadeType.*;

import java.util.ArrayList;
import java.util.List;

import com.back.global.jpa.entity.BaseEntity;
import com.back.global.jpa.entity.BaseManualIdAndTime;
import com.back.shared.cash.dto.WalletDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CASH_WALLET")
@NoArgsConstructor
@Getter
public class Wallet extends BaseManualIdAndTime {
	@ManyToOne(fetch = FetchType.LAZY) // 1:1 관계지만, Lazy Loading 및 비관적 락 사용을 위해 ManyToOne 사용
	private CashMember holder;

	@Getter
	private long balance;

	@OneToMany(mappedBy = "wallet", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
	private final List<CashLog> cashLogs = new ArrayList<>();

	public Wallet(CashMember holder) {
		super(holder.getId());
		this.holder = holder;
	}

	public boolean hasBalance() {
		return balance > 0;
	}

	/**
	 * 입금 메서드
	 *
	 * @param amount      캐시 수량
	 * @param eventType   캐시 이벤트 타입
	 * @param relTypeCode reference Type Code
	 * @param relId       reference ID
	 */
	public void credit(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
		balance += amount;

		addCashLog(amount, eventType, relTypeCode, relId);
	}

	/**
	 * @param amount    캐시 수량
	 * @param eventType 캐시 이벤트 타입
	 * @param rel       reference entity
	 */
	public void credit(long amount, CashLog.EventType eventType, BaseEntity rel) {
		credit(amount, eventType, rel.getModelTypeCode(), rel.getId());
	}

	/**
	 * @param amount    캐시 수량
	 * @param eventType 캐시 이벤트 타입
	 */
	public void credit(long amount, CashLog.EventType eventType) {
		credit(amount, eventType, holder);
	}

	/**
	 * @param amount      캐시 수량
	 * @param eventType   캐시 이벤트 타입
	 * @param relTypeCode reference Type Code
	 * @param relId       reference ID
	 */
	public void debit(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
		balance -= amount;

		addCashLog(-amount, eventType, relTypeCode, relId);
	}

	public void debit(long amount, CashLog.EventType eventType, BaseEntity rel) {
		debit(amount, eventType, rel.getModelTypeCode(), rel.getId());
	}

	public void debit(long amount, CashLog.EventType eventType) {
		debit(amount, eventType, holder);
	}

	private CashLog addCashLog(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
		CashLog cashLog = new CashLog(
			eventType,
			relTypeCode,
			relId,
			holder,
			this,
			amount,
			balance
		);

		cashLogs.add(cashLog);

		return cashLog;
	}

	public WalletDto toDto() {
		return new WalletDto(
			this.getId(),
			this.getHolder().getId(),
			this.getHolder().getUsername(),
			this.getBalance(),
			this.getCreateDate(),
			this.getModifyDate()
		);
	}
}
