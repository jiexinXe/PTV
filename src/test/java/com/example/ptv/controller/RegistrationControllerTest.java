package com.example.ptv.controller;

import com.example.ptv.service.RegistrationService;
import com.example.ptv.utils.R;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class RegistrationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void registerUserSuccess() throws Exception {
        when(registrationService.registerUser(any(Map.class))).thenReturn(true);

        mockMvc.perform(post("/register")
                        .param("username", "testuser")
                        .param("password", "testpass"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("注册成功"))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").value("success"));
    }

    @Test
    void registerUserFailure() throws Exception {
        when(registrationService.registerUser(any(Map.class))).thenReturn(false);

        mockMvc.perform(post("/register")
                        .param("username", "testuser")
                        .param("password", "testpass"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("注册失败"))
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("未知异常，请联系管理员"));
    }
}
