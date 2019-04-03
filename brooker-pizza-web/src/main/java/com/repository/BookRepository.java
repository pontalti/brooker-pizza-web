package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Book;
import com.model.enums.BookEnum;

/**
 * @author Pontalti X
 *
 */
public interface BookRepository extends JpaRepository<Book, Long> {
	
    @Query("SELECT b FROM Book b WHERE b.status = :status")
    public List<Book> retrieveByStatus(@Param("status") BookEnum status);
}
