package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.FinantialInstrument;
import com.repository.FinantialInstrumentRepository;

/**
 * @author Pontalti X
 *
 */
@Service("FinantialInstrumentService")
@Transactional
public class FinantialInstrumentServiceImpl implements FinantialInstrumentService<FinantialInstrument> {

	@Autowired
	private FinantialInstrumentRepository repository;
	
	public FinantialInstrumentServiceImpl() {
		super();
	}

	@Override
	public List<FinantialInstrument> findAll() {
		return this.repository.findAll();
	}

	@Override
	public FinantialInstrument findById(Long id) {
		return this.repository.findById(id).isPresent()?this.repository.findById(id).get():null;
	}

	@Override
	public FinantialInstrument saveOrUpdate(FinantialInstrument order) {
		return this.repository.saveAndFlush(order);
	}

	@Override
	public List<Object[]> retrieveStatistics() {
		return this.repository.retrieveStatistics();
	}
	
}
