package com.unibuc.bookmanagement.unit_tests.endpoints;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.unibuc.bookmanagement.controllers.UserController;
import com.unibuc.bookmanagement.services.UserService;

@AutoConfigureMockMvc(addFilters = false) 
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // @Test
    // public void testLoginPage() throws Exception {
    //     mockMvc.perform(get("/users/login"))
    //         .andExpect(status().isOk())
    //         .andExpect(view().name("login"));
    // }

    @Test
    public void testShowRegisterForm() throws Exception {
        mockMvc.perform(get("/users/register"))
            .andExpect(status().isOk())
            .andExpect(view().name("register"));
    }
}


