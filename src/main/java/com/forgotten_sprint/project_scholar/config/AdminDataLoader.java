
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

            User admin = new User();

            admin.setUsername("devadmin");
            admin.setEmail("devadmin@projectscholar.local");

            // Shared password for testing only SORRY PROFESSOR tehe
            admin.setPassword(
                passwordEncoder.encode("ScholarDemo2026!")
            );

            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println("Development ADMIN account created.");
        };
    }
}
