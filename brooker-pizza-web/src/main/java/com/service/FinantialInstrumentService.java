package com.service;

import java.util.List;

/**
 * @author Pontalti X
 *
 */
public interface FinantialInstrumentService<T> extends GenericService<T> {
	
	public List<Object[]> retrieveStatistics();
	
}
