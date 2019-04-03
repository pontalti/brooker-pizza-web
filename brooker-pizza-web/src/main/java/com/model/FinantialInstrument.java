package com.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Pontalti X
 *
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="FINAN_INSTRUMENT_TB")
public class FinantialInstrument implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="f_i_name_id")
	private String finantialIstrumentNameId;
	
	@Column
	private String name;
	
	@Column
	private Double price;
	
	@Column
	private Double bid;
	
	@Column
	private Double ask;
	
	@Column
	private Integer quantity;
	
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<Order> orders;
	
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@OneToOne(mappedBy = "instrument")
	private Book book;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFinantialIstrumentNameId() {
		return finantialIstrumentNameId;
	}

	public void setFinantialIstrumentNameId(String finantialIstrumentNameId) {
		this.finantialIstrumentNameId = finantialIstrumentNameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
