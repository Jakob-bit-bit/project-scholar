
package com.forgotten_sprint.project_scholar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityAuthorizationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void anonymousUserShouldBeRedirectedToLogin() throws Exception {
        mockMvc.perform(get("/courses/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void studentCannotCreateCourses() throws Exception {
        mockMvc.perform(get("/courses/new"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void instructorCanAccessCourseCreation() throws Exception {
        mockMvc.perform(get("/courses/new"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void studentCannotAccessAdmin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanAccessCourseCreation() throws Exception {
        mockMvc.perform(get("/courses/new"))
                .andExpect(status().isOk());
    }

	@Test
	@WithMockUser(roles = "STUDENT")
	void studentCannotSubmitCourseCreation() throws Exception {
	    mockMvc.perform(post("/courses")
	            .with(csrf()))
	            .andExpect(status().isForbidden());
	}

}
