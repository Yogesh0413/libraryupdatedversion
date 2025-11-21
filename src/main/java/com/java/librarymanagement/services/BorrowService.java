package com.java.librarymanagement.services;

import com.java.librarymanagement.entities.User;
import com.java.librarymanagement.repos.UserRepository;
import com.java.librarymanagement.entities.Book;
import com.java.librarymanagement.repos.BookRepository;
import com.java.librarymanagement.entities.BorrowRecord;
import com.java.librarymanagement.repos.BorrowRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BorrowService {

    private final BorrowRecordRepository borrowRecordRepo;
    private final BookRepository bookRepo;
    private final UserRepository userRepo;

    public BorrowService(BorrowRecordRepository borrowRecordRepo, BookRepository bookRepo, UserRepository userRepo) {
        this.borrowRecordRepo = borrowRecordRepo;
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    // ============================
    // BORROW BOOK
    // ============================
    public String borrowBook(Long bookId, String email) {

        User student = userRepo.findByEmail(email);
        if (student == null) {
            return "User not found!";
        }

        Book book = bookRepo.findById(bookId).orElse(null);
        if (book == null) {
            return "Book not found!";
        }

        // Check if already borrowed by this student
        if (borrowRecordRepo.findByStudentAndBookAndReturned(student, book, false).isPresent()) {
            return "You have already borrowed this book!";
        }

        // Check if another student borrowed it
        var borrowedBySomeone = borrowRecordRepo.findByBookAndReturned(book, false);
        if (borrowedBySomeone.isPresent()) {
            return "This book is already borrowed by another student!";
        }

        // Borrow it
        BorrowRecord record = new BorrowRecord(student, book);
        borrowRecordRepo.save(record);

        book.setAvailable(false);
        bookRepo.save(book);

        return "Book borrowed successfully!";
    }

    // ============================
    // RETURN BOOK
    // ============================
    public String returnBook(Long bookId) {

        Book book = bookRepo.findById(bookId).orElse(null);
        if (book == null) {
            return "Book not found!";
        }

        if (book.isAvailable()) {
            return "Book is already available!";
        }

        // Find the active borrow record
        var borrowRecord = borrowRecordRepo.findByBookAndReturned(book, false);
        if (borrowRecord.isEmpty()) {
            return "No borrow record found!";
        }

        // Mark as returned
        BorrowRecord record = borrowRecord.get();
        record.setReturned(true);
        record.setReturnDate(LocalDateTime.now());
        borrowRecordRepo.save(record);

        // Make book available again
        book.setAvailable(true);
        bookRepo.save(book);

        return "Book returned successfully!";
    }
}