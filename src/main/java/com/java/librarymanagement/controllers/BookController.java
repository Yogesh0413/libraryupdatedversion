package com.java.librarymanagement.controllers;

import com.java.librarymanagement.entities.User;
import com.java.librarymanagement.entities.Book;
import com.java.librarymanagement.services.BookService;
import com.java.librarymanagement.services.BorrowService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BorrowService borrowService;

    public BookController(BookService bookService, BorrowService borrowService) {
        this.bookService = bookService;
        this.borrowService = borrowService;
    }

    // ============================
    // SHOW ALL BOOKS
    // ============================
    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("newBook", new Book());
        return "books";
    }

    // ============================
    // ADD BOOK (from modal)
    // ============================
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        book.setAvailable(true);
        bookService.addBook(book);
        return "redirect:/dashboard/admin";
    }

    // ============================
    // DELETE BOOK
    // ============================
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/dashboard/admin";
    }

    // ============================
    // UPDATE BOOK (AJAX for inline edit)
    // ============================
    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<String> editBook(@RequestParam Long id,
                                           @RequestParam String title,
                                           @RequestParam String author) {

        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.badRequest().body("Book not found");
        }

        book.setTitle(title);
        book.setAuthor(author);
        bookService.updateBook(book);

        return ResponseEntity.ok("Book updated successfully");
    }

    // ============================
    // BORROW BOOK
    // ============================
    @PostMapping("/borrow")
    @ResponseBody
    public ResponseEntity<String> borrowBook(@RequestParam Long bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body("Please login first");
        }

        String result = borrowService.borrowBook(bookId, user.getEmail());

        if (result.contains("successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    // ============================
    // RETURN BOOK
    // ============================
    @PostMapping("/return")
    @ResponseBody
    public ResponseEntity<String> returnBook(@RequestParam Long bookId) {
        String result = borrowService.returnBook(bookId);

        if (result.contains("successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}