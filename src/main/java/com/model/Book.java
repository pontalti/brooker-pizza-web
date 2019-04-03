package com.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.model.enums.BookEnum;

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
@Table(name="BOOK_TB")
public class Book implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@EqualsAndHashCode.Exclude
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "fin_inst_id", referencedColumnName = "id")
	private FinantialInstrument instrument;

	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@OneToMany(mappedBy="book", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<Order> orders;
	
	@Enumerated(EnumType.ORDINAL)
	private BookEnum status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FinantialInstrument getInstrument() {
		return instrument;
	}

	public void setInstrument(FinantialInstrument instrument) {
		this.instrument = instrument;
	}

	public Set<Order> getOrders() {
		return Collections.unmodifiableSet(orders);
	}

	@Transient
	public void addOrder(final Order order) {
		if( null == this.orders ) {
			this.orders = new HashSet<Order>();
		}
		this.orders.add(order);
	}
	
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public BookEnum getStatus() {
		return status;
	}

	public void setStatus(BookEnum status) {
		this.status = status;
	}
	
}
