package com.forgotten_sprint.project_scholar.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.forgotten_sprint.project_scholar.model.Role;
import com.forgotten_sprint.project_scholar.model.User;
import com.forgotten_sprint.project_scholar.repository.UserRepository;

@Controller
public class AuthTestController {
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthTestController(UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login-test";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register-test";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(defaultValue = "false")
                boolean registerAsInstructor,
            Model model) {

        username = username.trim();
        email = email.trim();

        if (username.isBlank() || email.isBlank()
                || password.length() < 8) {
            model.addAttribute("error",
                "Enter a username, email and password of at least 8 characters.");
            return "register-test";
        }

        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error",
                "Username already exists.");
            return "register-test";
        }

        if (userRepository.existsByEmail(email)) {
            model.addAttribute("error",
                "Email already exists.");
            return "register-test";
        }

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);

        // Never save the original password.
        user.setPassword(passwordEncoder.encode(password));

        // The registration form cannot assign ADMIN. We could change that later but it depends
        user.setRole(registerAsInstructor
                ? Role.INSTRUCTOR
                : Role.STUDENT);

        userRepository.save(user);

        return "redirect:/login?registered";
    }
}
