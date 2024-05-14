package com.example.ptv.controller;

import com.example.ptv.service.Imp.userServiceImp;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private userServiceImp userServiceimp;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getUserinfo() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "User Info");

        when(userServiceimp.getUserinfo(eq(1))).thenReturn(rest);

        mockMvc.perform(get("/user/getUserinfo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("User Info"));
    }

    @Test
    void changeUserinfo() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "User Info Changed");

        when(userServiceimp.changeUserinfo(any(String.class), any(String.class), any(Map.class))).thenReturn(rest);

        Map<String, Object> changeVariable = new HashMap<>();
        changeVariable.put("address", "newAddress");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userId", "1");
        requestBody.put("changeItem", "address");
        requestBody.put("changeVariable", objectMapper.writeValueAsString(changeVariable));

        mockMvc.perform(post("/user/changeUserinfo")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("User Info Changed"));
    }
}
