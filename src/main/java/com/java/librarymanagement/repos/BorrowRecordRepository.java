package com.java.librarymanagement.repos;

import com.java.librarymanagement.entities.Book;
import com.java.librarymanagement.entities.User;
import com.java.librarymanagement.entities.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    // Check if student already borrowed and not returned
    Optional<BorrowRecord> findByStudentAndBookAndReturned(User student, Book book, boolean returned);

    // Check which student has borrowed the book
    Optional<BorrowRecord> findByBookAndReturned(Book book, boolean returned);

    // Find by book ID directly
    Optional<BorrowRecord> findByBookIdAndReturned(Long bookId, boolean returned);
}
