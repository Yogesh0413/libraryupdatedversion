package com.java.librarymanagement.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrow_records")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    private boolean returned;

    public BorrowRecord() {}

    public BorrowRecord(User student, Book book) {
        this.student = student;
        this.book = book;
        this.borrowDate = LocalDateTime.now();
        this.returnDate = borrowDate.plusDays(7);
        this.returned = false;
    }

// ✅ GETTERS & SETTERS

    public Long getId() {
        return id;
    }

    public User getStudent() {   // ← THIS IS WHAT YOU WERE MISSING
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
