package com.forgotten_sprint.project_scholar.config;

import com.forgotten_sprint.project_scholar.model.Role;
import com.forgotten_sprint.project_scholar.model.User;
import com.forgotten_sprint.project_scholar.repository.UserRepository;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class AdminDataLoader {

    @Bean
    ApplicationRunner createDevAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.existsByUsername("devadmin")) {
                return;
            }

            String adminPassword =
                    System.getenv("PROJECT_SCHOLAR_ADMIN_PASSWORD");

            if (adminPassword == null || adminPassword.isBlank()) {
                throw new IllegalStateException(
                    "Set PROJECT_SCHOLAR_ADMIN_PASSWORD for the dev admin."
                );
            }

            User admin = new User();
            admin.setUsername("devadmin");
            admin.setEmail("devadmin@projectscholar.local");
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println("Development ADMIN account created.");
        };
    }
}
