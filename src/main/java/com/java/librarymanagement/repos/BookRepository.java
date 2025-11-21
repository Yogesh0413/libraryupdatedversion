package com.java.librarymanagement.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.java.librarymanagement.entities.Book;


public interface BookRepository extends JpaRepository<Book, Long> {
}
