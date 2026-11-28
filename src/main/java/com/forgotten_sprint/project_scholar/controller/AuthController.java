package com.forgotten_sprint.project_scholar.controller;
import com.forgotten_sprint.project_scholar.dto.RegistrationForm;
import com.forgotten_sprint.project_scholar.model.Role;
import com.forgotten_sprint.project_scholar.model.User;
import com.forgotten_sprint.project_scholar.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Display login page
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("pageTitle", "Login");
        return "login";
    }

    // Display registration page
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        model.addAttribute("pageTitle", "Register");
        return "register";
    }

    // Process registration
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registrationForm")
            RegistrationForm registrationForm,
            BindingResult bindingResult,
            Model model) {

        String username = registrationForm.getUsername() == null
                ? "" : registrationForm.getUsername().trim();

        String email = registrationForm.getEmail() == null
                ? "" : registrationForm.getEmail().trim().toLowerCase();

        //Check for duplicate usern
        if (!username.isBlank()
                && userRepository.existsByUsername(username)) {

            bindingResult.rejectValue(
                    "username",
                    "duplicate.username",
                    "This username is already taken."
            );
        }

        // Check for duplicate emails
        if (!email.isBlank()
                && userRepository.existsByEmail(email)) {

            bindingResult.rejectValue(
                    "email",
                    "duplicate.email",
                    "This email is already registered."
            );
        }

        // Return to registration form if errors exist
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Register");
            return "register";
        }

        // Create new user
        User user = new User();

        user.setUsername(username);
        user.setEmail(email);

        // Encode password before saving
        user.setPassword(
                passwordEncoder.encode(registrationForm.getPassword())
        );

        // Assign STUDENT or INSTRUCTOR
        // ADMIN cannot be assigned through public registration
        user.setRole(registrationForm.isRegisterAsInstructor()
                ? Role.INSTRUCTOR
                : Role.STUDENT);

        userRepository.save(user);

        return "redirect:/login?registered";
    }
}
