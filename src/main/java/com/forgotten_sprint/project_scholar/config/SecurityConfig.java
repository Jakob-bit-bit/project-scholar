package com.forgotten_sprint.project_scholar.config;

import jakarta.servlet.DispatcherType;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;


import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                .requestMatchers(
                    "/",
                    "/about",
                    "/explore-learning",
                    "/catalog",
                    "/login",
                    "/register",
                    "/error",
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()

                // H2 console: ADMIN only
                .requestMatchers(PathRequest.toH2Console())
                    .hasRole("ADMIN")

                // Public pages
                .requestMatchers(
                    "/",
                    "/about",
                    "/explore-learning",
                    "/catalog",
                    "/login",
                    "/register",
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()

                // Course creation: INSTRUCTOR or ADMIN
                .requestMatchers(HttpMethod.GET, "/courses/new")
                    .hasAnyRole("INSTRUCTOR", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/courses")
                    .hasAnyRole("INSTRUCTOR", "ADMIN")

                // Admin pages
                .requestMatchers("/admin", "/admin/**")
                    .hasRole("ADMIN")

                // All remaining routes require authentication
                .anyRequest().authenticated()
            )

            // Custom login page
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/catalog", true)
                .permitAll()
            )

            // Logout
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )

            // CSRF exception only for H2 Console
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(PathRequest.toH2Console())
            )

            // H2 Console uses frames
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }
}

