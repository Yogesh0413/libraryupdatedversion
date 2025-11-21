package com.java.librarymanagement.controllers;

import com.java.librarymanagement.entities.User;
import com.java.librarymanagement.entities.Book;
import com.java.librarymanagement.services.BookService;
import com.java.librarymanagement.entities.BorrowRecord;
import com.java.librarymanagement.repos.BorrowRecordRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final BookService bookService;
    private final BorrowRecordRepository borrowRecordRepository;

    public DashboardController(BookService bookService,
                               BorrowRecordRepository borrowRecordRepository) {
        this.bookService = bookService;
        this.borrowRecordRepository = borrowRecordRepository;
    }

    // ============================
    // ADMIN DASHBOARD
    // ============================
    @GetMapping("/admin")
    public String adminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/auth/login";
        }

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/dashboard/student";
        }

        List<Book> books = bookService.getAllBooks();

        // Create a map of bookId -> borrower name
        Map<Long, String> borrowerMap = new HashMap<>();

        for (Book book : books) {
            if (!book.isAvailable()) {
                BorrowRecord record = borrowRecordRepository
                        .findByBookAndReturned(book, false)
                        .orElse(null);

                if (record != null && record.getStudent() != null) {
                    borrowerMap.put(book.getId(), record.getStudent().getName());
                }
            }
        }

        model.addAttribute("books", books);
        model.addAttribute("borrowerMap", borrowerMap);
        model.addAttribute("newBook", new Book());

        return "books";
    }

    // ============================
    // STUDENT DASHBOARD
    // ============================
    @GetMapping("/student")
    public String studentDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/auth/login";
        }

        List<Book> books = bookService.getAllBooks();

        // Maps to track who borrowed what
        Map<Long, Boolean> borrowedByUser = new HashMap<>();
        Map<Long, Boolean> borrowedByOther = new HashMap<>();

        for (Book book : books) {
            BorrowRecord record = borrowRecordRepository
                    .findByBookAndReturned(book, false)
                    .orElse(null);

            if (record != null) {
                if (record.getStudent().getId() == user.getId()) {
                    borrowedByUser.put(book.getId(), true);
                } else {
                    borrowedByOther.put(book.getId(), true);
                }
            }
        }

        model.addAttribute("books", books);
        model.addAttribute("user", user);
        model.addAttribute("borrowedByUser", borrowedByUser);
        model.addAttribute("borrowedByOther", borrowedByOther);

        return "studentbook";
    }
}