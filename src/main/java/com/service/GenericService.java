package com.service;

import java.util.List;

/**
 * @author Pontalti X
 *
 */
public interface GenericService<T> {
	public List<T> findAll();
	public T findById(Long id);
	public T saveOrUpdate(T obj);
}
