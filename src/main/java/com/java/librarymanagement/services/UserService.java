package com.java.librarymanagement.services;

import com.java.librarymanagement.entities.User;
import com.java.librarymanagement.repos.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // -----------------------------------------
    // ✅ SIGNUP
    // -----------------------------------------
    public String signup(User user) {

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Email already registered!";
        }

        // Default role = STUDENT
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("STUDENT");
        }

        userRepository.save(user);
        return "Signup successful!";
    }

    // -----------------------------------------
    // ✅ LOGIN
    // -----------------------------------------
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            System.out.println("No user found with email: " + email);
            return null;
        }

        if (!user.getPassword().equals(password)) {
            System.out.println("Password mismatch!");
            return null;
        }

        return user;
    }

    // -----------------------------------------
    // ✅ ADMIN FUNCTION: Get all users
    // -----------------------------------------
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    // -----------------------------------------
    // ✅ ADMIN FUNCTION: Delete user by ID
    // -----------------------------------------
    public String deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            return "User not found!";
        }

        userRepository.deleteById(id);
        return "User deleted successfully!";
    }

    // -----------------------------------------
    // ✅ ADMIN FUNCTION: Update user info/role
    // -----------------------------------------
    public User updateUser(int id, User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update name if provided
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }

        // Update role if provided
        if (updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }

        // Update password if provided
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }

        return userRepository.save(existingUser);
    }
}
