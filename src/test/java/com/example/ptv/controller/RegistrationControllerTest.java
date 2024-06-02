package com.example.ptv.controller;

import com.example.ptv.service.RegistrationService;
import com.example.ptv.utils.R;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class RegistrationControllerTest {

    @MockBean
    private RegistrationService registrationService;

    private MockMvc mockMvc;

    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    void list_whenRegistrationSuccess() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("username", "testUser");
        params.put("password", "testPassword");

        when(registrationService.registerUser(params)).thenReturn(true);

        mockMvc.perform(post("/register")
                        .param("username", "testUser")
                        .param("password", "testPassword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.msg", is("success")))
                .andExpect(jsonPath("$.result", is("注册成功")));
    }

    @Test
    void list_whenRegistrationFails() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("username", "testUser");
        params.put("password", "testPassword");

        when(registrationService.registerUser(params)).thenReturn(false);

        mockMvc.perform(post("/register")
                        .param("username", "testUser")
                        .param("password", "testPassword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(500)))
                .andExpect(jsonPath("$.msg", is("未知异常，请联系管理员")))
                .andExpect(jsonPath("$.result", is("注册失败")));
    }
}
