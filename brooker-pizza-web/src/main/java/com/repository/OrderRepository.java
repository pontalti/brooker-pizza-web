package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Order;

/**
 * @author Pontalti X
 *
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
