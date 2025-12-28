package com.back.boundedcontext.market.domain;

import static jakarta.persistence.FetchType.*;

import com.back.global.jpa.entity.BaseIdAndTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "MARKET_PRODUCT")
public class Product extends BaseIdAndTime {
	@ManyToOne(fetch = LAZY)
	private MarketMember seller;
	private String sourceTypeCode;
	private int sourceId;
	private String name;
	private String description;
	private long price;
	private long salePrice;
}
