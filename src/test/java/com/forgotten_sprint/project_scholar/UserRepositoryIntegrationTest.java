
package com.forgotten_sprint.project_scholar;

import static org.junit.jupiter.api.Assertions.*;

import com.forgotten_sprint.project_scholar.model.Role;
import com.forgotten_sprint.project_scholar.model.User;
import com.forgotten_sprint.project_scholar.repository.UserRepository;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldStoreEncodedPasswordInDatabase() {

        String rawPassword = "Student123!";

        User user = new User();
        user.setUsername("integrationstudent");
        user.setEmail("integrationstudent@example.com");
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.STUDENT);

        User savedUser = userRepository.saveAndFlush(user);
        entityManager.clear();
        User storedUser = userRepository
                .findByUsername(savedUser.getUsername())
                .orElseThrow();

        assertEquals(Role.STUDENT, storedUser.getRole());

        assertNotEquals(rawPassword, storedUser.getPassword());

        assertTrue(storedUser.getPassword().startsWith("$2"));

        assertTrue(passwordEncoder.matches(
                rawPassword,
                storedUser.getPassword()
        ));
    }
}
