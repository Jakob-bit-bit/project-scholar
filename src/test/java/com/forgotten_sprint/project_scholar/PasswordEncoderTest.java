package com.forgotten_sprint.project_scholar;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordEncoderTest {

	private final PasswordEncoder encoder = new BCryptPasswordEncoder();
	@Test
	void passwordShouldBeEncodedWithBCrypt() {

        String rawPassword = "Student123!";

        String encodedPassword =
                encoder.encode(rawPassword);

        assertNotEquals(rawPassword, encodedPassword);

        assertTrue(encodedPassword.startsWith("$2"));

        assertTrue(encoder.matches(
                rawPassword, encodedPassword
        ));

        assertFalse(encoder.matches(
                "WrongPassword", encodedPassword
        ));
    }
	}

