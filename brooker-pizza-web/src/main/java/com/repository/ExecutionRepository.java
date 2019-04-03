package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Execution;

/**
 * @author Pontalti X
 *
 */
public interface ExecutionRepository extends JpaRepository<Execution, Long> {
	
}
