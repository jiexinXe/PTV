package com.example.ptv.controller;

import com.example.ptv.entity.User;
import com.example.ptv.service.Imp.userServiceImp;
import com.example.ptv.utils.Rest;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class UserControllerTest {

    @MockBean
    private userServiceImp userServiceimp;

    private MockMvc mockMvc;

    @InjectMocks
    private userController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getUserinfo() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("testUser");
        Rest rest = new Rest(200, user, "获取成功");
        when(userServiceimp.getUserinfo(1)).thenReturn(rest);

        mockMvc.perform(get("/user/getUserinfo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("testUser")))
                .andExpect(jsonPath("$.msg", is("获取成功")));
    }

    @Test
    void changeUserinfo() throws Exception {
        Rest rest = new Rest(200, "修改成功");
        when(userServiceimp.changeUserinfo(eq("1"), eq("profile"), any(Map.class))).thenReturn(rest);

        String requestBody = "{ \"userId\": \"1\", \"changeItem\": \"profile\", \"changeVariable\": \"{ \\\"name\\\": \\\"newName\\\" }\" }";

        mockMvc.perform(post("/user/changeUserinfo")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("修改成功")));
    }
}
