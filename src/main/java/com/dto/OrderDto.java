package com.dto;

import java.io.Serializable;

import com.model.enums.OrderEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class OrderDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long bookId;
	private Double instrumentPrice;
	private Double limitPrice;
	private Double orderPrice;
	private Integer quantity;
	private OrderEnum type;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Double getInstrumentPrice() {
		return instrumentPrice;
	}

	public void setInstrumentPrice(Double instrumentPrice) {
		this.instrumentPrice = instrumentPrice;
	}

	public Double getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(Double limitPrice) {
		this.limitPrice = limitPrice;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public OrderEnum getType() {
		return type;
	}

	public void setType(OrderEnum type) {
		this.type = type;
	}

}
