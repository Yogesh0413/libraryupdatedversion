package com.java.librarymanagement.services;

import com.java.librarymanagement.entities.Book;
import com.java.librarymanagement.repos.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    //addnewbook
    public Book addBook(Book book){
        return bookRepository.save(book);
    }
    //deletebookbyid
     public void deleteBook(Long id) {
            bookRepository.deleteById(id);
       }
    //showallbook
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    //findbookbyid
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    //update book (title,author)
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }
    //availability
    public void updateAvailability(Long id, boolean available) {
        Book book = getBookById(id);
        if (book != null) {
            book.setAvailable(available);
            bookRepository.save(book);
        }
    }


}
