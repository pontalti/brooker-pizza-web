package com.builder;

import java.io.Serializable;

import com.dto.StatisticDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StatisticBuilder implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String status;
	private String type;
	private Integer total;
	private Integer minQuantity;
	private Integer maxQuantity;
	private Double avgQuantity;

	private Double minInstrumentPrice;
	private Double maxInstrumentPrice;
	private Double avgInstrumentPrice;

	private Double minLimitPrice;
	private Double maxLimitPrice;
	private Double avgLimitPrice;

	private Double minOrderPrice;
	private Double maxOrderPrice;
	private Double avgOrderPrice;
	
	public StatisticBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public StatisticBuilder setStatus(String status) {
		this.status = status;
		return this;
	}

	public StatisticBuilder setType(String type) {
		this.type = type;
		return this;
	}

	public StatisticBuilder setTotal(Integer total) {
		this.total = total;
		return this;
	}

	public StatisticBuilder setMinQuantity(Integer minQuantity) {
		this.minQuantity = minQuantity;
		return this;
	}

	public StatisticBuilder setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
		return this;
	}

	public StatisticBuilder setAvgQuantity(Double avgQuantity) {
		this.avgQuantity = avgQuantity;
		return this;
	}

	public StatisticBuilder setMinInstrumentPrice(Double minInstrumentPrice) {
		this.minInstrumentPrice = minInstrumentPrice;
		return this;
	}

	public StatisticBuilder setMaxInstrumentPrice(Double maxInstrumentPrice) {
		this.maxInstrumentPrice = maxInstrumentPrice;
		return this;
	}

	public StatisticBuilder setAvgInstrumentPrice(Double avgInstrumentPrice) {
		this.avgInstrumentPrice = avgInstrumentPrice;
		return this;
	}

	public StatisticBuilder setMinLimitPrice(Double minLimitPrice) {
		this.minLimitPrice = minLimitPrice;
		return this;
	}

	public StatisticBuilder setMaxLimitPrice(Double maxLimitPrice) {
		this.maxLimitPrice = maxLimitPrice;
		return this;
	}

	public StatisticBuilder setAvgLimitPrice(Double avgLimitPrice) {
		this.avgLimitPrice = avgLimitPrice;
		return this;
	}

	public StatisticBuilder setMinOrderPrice(Double minOrderPrice) {
		this.minOrderPrice = minOrderPrice;
		return this;
	}

	public StatisticBuilder setMaxOrderPrice(Double maxOrderPrice) {
		this.maxOrderPrice = maxOrderPrice;
		return this;
	}

	public StatisticBuilder setAvgOrderPrice(Double avgOrderPrice) {
		this.avgOrderPrice = avgOrderPrice;
		return this;
	}
	
	public StatisticDto build() {
		return new StatisticDto(	this.id, 
									this.status, 
									this.type, 
									this.total, 
									this.minQuantity, 
									this.maxQuantity, 
									this.avgQuantity, 
									this.minInstrumentPrice, 
									this.maxInstrumentPrice, 
									this.avgInstrumentPrice, 
									this.minLimitPrice, 
									this.maxLimitPrice, 
									this.avgLimitPrice, 
									this.minOrderPrice, 
									this.maxOrderPrice, 
									this.avgOrderPrice);
	}

}
