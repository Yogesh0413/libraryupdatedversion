package com.java.librarymanagement.controllers;
import com.java.librarymanagement.entities.User;
import com.java.librarymanagement.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ============================
    // SHOW LOGIN PAGE
    // ============================
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // ============================
    // LOGIN SUBMISSION
    // ============================
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,  // ✅ Add this
                        Model model) {

        User user = userService.login(email, password);

        if (user == null) {
            model.addAttribute("error", "Incorrect email or password");
            return "login";
        }

        // ✅ Store user in session - THIS IS CRITICAL!
        session.setAttribute("user", user);
        session.setAttribute("role", user.getRole());

        System.out.println("✅ Login successful for: " + user.getEmail() + " with role: " + user.getRole());

        // Redirect by role
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/dashboard/admin";
        } else {
            return "redirect:/dashboard/student";
        }
    }

    // ============================
    // SHOW SIGNUP PAGE
    // ============================
    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // ============================
    // SIGNUP SUBMISSION
    // ============================
    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, Model model) {

        String response = userService.signup(user);

        if (response.equals("Email already registered!")) {
            model.addAttribute("error", response);
            return "signup";
        }

        return "redirect:/auth/login";
    }

    // ============================
    // LOGOUT (Optional but recommended)
    // ============================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}