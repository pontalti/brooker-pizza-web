package com.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class StatisticDto implements Serializable {

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

	public StatisticDto(String id, String status, String type, Integer total, Integer minQuantity, Integer maxQuantity,
			Double avgQuantity, Double minInstrumentPrice, Double maxInstrumentPrice, Double avgInstrumentPrice,
			Double minLimitPrice, Double maxLimitPrice, Double avgLimitPrice, Double minOrderPrice,
			Double maxOrderPrice, Double avgOrderPrice) {
		super();
		this.id = id;
		this.status = status;
		this.type = type;
		this.total = total;
		this.minQuantity = minQuantity;
		this.maxQuantity = maxQuantity;
		this.avgQuantity = avgQuantity;
		this.minInstrumentPrice = minInstrumentPrice;
		this.maxInstrumentPrice = maxInstrumentPrice;
		this.avgInstrumentPrice = avgInstrumentPrice;
		this.minLimitPrice = minLimitPrice;
		this.maxLimitPrice = maxLimitPrice;
		this.avgLimitPrice = avgLimitPrice;
		this.minOrderPrice = minOrderPrice;
		this.maxOrderPrice = maxOrderPrice;
		this.avgOrderPrice = avgOrderPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(Integer minQuantity) {
		this.minQuantity = minQuantity;
	}

	public Integer getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public Double getAvgQuantity() {
		return avgQuantity;
	}

	public void setAvgQuantity(Double avgQuantity) {
		this.avgQuantity = avgQuantity;
	}

	public Double getMinInstrumentPrice() {
		return minInstrumentPrice;
	}

	public void setMinInstrumentPrice(Double minInstrumentPrice) {
		this.minInstrumentPrice = minInstrumentPrice;
	}

	public Double getMaxInstrumentPrice() {
		return maxInstrumentPrice;
	}

	public void setMaxInstrumentPrice(Double maxInstrumentPrice) {
		this.maxInstrumentPrice = maxInstrumentPrice;
	}

	public Double getAvgInstrumentPrice() {
		return avgInstrumentPrice;
	}

	public void setAvgInstrumentPrice(Double avgInstrumentPrice) {
		this.avgInstrumentPrice = avgInstrumentPrice;
	}

	public Double getMinLimitPrice() {
		return minLimitPrice;
	}

	public void setMinLimitPrice(Double minLimitPrice) {
		this.minLimitPrice = minLimitPrice;
	}

	public Double getMaxLimitPrice() {
		return maxLimitPrice;
	}

	public void setMaxLimitPrice(Double maxLimitPrice) {
		this.maxLimitPrice = maxLimitPrice;
	}

	public Double getAvgLimitPrice() {
		return avgLimitPrice;
	}

	public void setAvgLimitPrice(Double avgLimitPrice) {
		this.avgLimitPrice = avgLimitPrice;
	}

	public Double getMinOrderPrice() {
		return minOrderPrice;
	}

	public void setMinOrderPrice(Double minOrderPrice) {
		this.minOrderPrice = minOrderPrice;
	}

	public Double getMaxOrderPrice() {
		return maxOrderPrice;
	}

	public void setMaxOrderPrice(Double maxOrderPrice) {
		this.maxOrderPrice = maxOrderPrice;
	}

	public Double getAvgOrderPrice() {
		return avgOrderPrice;
	}

	public void setAvgOrderPrice(Double avgOrderPrice) {
		this.avgOrderPrice = avgOrderPrice;
	}

}
