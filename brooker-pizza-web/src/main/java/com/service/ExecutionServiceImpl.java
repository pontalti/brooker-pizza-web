package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Execution;
import com.repository.ExecutionRepository;

/**
 * @author Pontalti X
 *
 */
@Service("ExecutionService")
@Transactional
public class ExecutionServiceImpl implements ExecutionService<Execution> {

	@Autowired
	private ExecutionRepository repository;
	
	public ExecutionServiceImpl() {
		super();
	}

	@Override
	public List<Execution> findAll() {
		return this.repository.findAll();
	}

	@Override
	public Execution findById(Long id) {
		return this.repository.findById(id).isPresent()?this.repository.findById(id).get():null;
	}

	@Override
	public Execution saveOrUpdate(Execution obj) {
		return this.repository.saveAndFlush(obj);
	}
	
}
