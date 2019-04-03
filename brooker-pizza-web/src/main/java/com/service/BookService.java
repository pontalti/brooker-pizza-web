package com.service;

import java.util.List;

import com.model.enums.BookEnum;

/**
 * @author Pontalti X
 * @param <T>
 *
 */
public interface BookService<T> extends GenericService<T> {

	public List<T> retrieveByStatus(BookEnum status);
	
}
