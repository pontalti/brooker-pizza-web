package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Book;
import com.model.enums.BookEnum;
import com.repository.BookRepository;

/**
 * @author Pontalti X
 *
 */
@Service("BookService")
@Transactional
public class BookServiceImpl implements BookService<Book> {

	@Autowired
	private BookRepository repository;
	
	public BookServiceImpl() {
		super();
	}

	@Override
	public List<Book> findAll() {
		return this.repository.findAll();
	}

	@Override
	public Book findById(Long id) {
		return this.repository.findById(id).isPresent()?this.repository.findById(id).get():null;
	}

	@Override
	public Book saveOrUpdate(Book obj) {
		return this.repository.saveAndFlush(obj);
	}
	
	@Override
	public List<Book> retrieveByStatus(BookEnum status){
		return this.repository.retrieveByStatus(status);
	}
	
}
