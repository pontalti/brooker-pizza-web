package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Order;
import com.repository.OrderRepository;

/**
 * @author Pontalti X
 *
 */
@Service("OrderService")
@Transactional
public class OrderServiceImpl implements OrderService<Order> {

	@Autowired
	private OrderRepository repository;
	
	public OrderServiceImpl() {
		super();
	}

	@Override
	public List<Order> findAll() {
		return this.repository.findAll();
	}

	@Override
	public Order findById(Long id) {
		return this.repository.findById(id).isPresent()?this.repository.findById(id).get():null;
	}

	@Override
	public Order saveOrUpdate(Order order) {
		return this.repository.saveAndFlush(order);
	}
	
}
