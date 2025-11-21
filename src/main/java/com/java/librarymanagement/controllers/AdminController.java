package com.java.librarymanagement.controllers;

import com.java.librarymanagement.entities.User;
import com.java.librarymanagement.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // ============================
    // LOAD USER MANAGEMENT PAGE
    // ============================
    @GetMapping("/manage-users")
    public String manageUsersPage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null || !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return "redirect:/auth/login";
        }

        model.addAttribute("users", userService.getAllUsers());
        return "usertable";
    }

    // ============================
    // GET ALL USERS (JSON for AJAX)
    // ============================
    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null || !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return ResponseEntity.status(403).body(null);
        }

        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ============================
    // UPDATE USER
    // ============================
    @PutMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<String> updateUser(@PathVariable int id,
                                             @RequestBody User updatedUser,
                                             HttpSession session) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null || !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return ResponseEntity.status(403).body("Access denied");
        }

        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        userService.updateUser(id,user);

        return ResponseEntity.ok("User updated successfully");
    }

    // ============================
    // DELETE USER
    // ============================
    @DeleteMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable int id, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null || !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return ResponseEntity.status(403).body("Access denied");
        }

        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}