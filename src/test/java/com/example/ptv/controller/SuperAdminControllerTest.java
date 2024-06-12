package com.example.ptv.controller;

import com.example.ptv.service.Imp.superAdminServiceImp;
import com.example.ptv.utils.Rest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class SuperAdminControllerTest {

    @MockBean
    private superAdminServiceImp superAdminServiceimp;

    private MockMvc mockMvc;

    @InjectMocks
    private superAdminController superAdminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(superAdminController).build();
    }

    @Test
    void getallUserinfo() throws Exception {
        Rest rest = new Rest(200, "user list", "获取成功");
        when(superAdminServiceimp.fgetallUserinfo()).thenReturn(rest);

        mockMvc.perform(get("/superAdmin/getallUserinfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data", is("user list")))
                .andExpect(jsonPath("$.msg", is("获取成功")));
    }

    @Test
    void getUserinfo() throws Exception {
        Rest rest = new Rest(200, "user info", "获取成功");
        when(superAdminServiceimp.fgetUserinfo("1")).thenReturn(rest);

        mockMvc.perform(get("/superAdmin/getUserinfo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data", is("user info")))
                .andExpect(jsonPath("$.msg", is("获取成功")));
    }

    @Test
    void changeUserinfo() throws Exception {
        Rest rest = new Rest(200, "修改成功");
        when(superAdminServiceimp.fchangeUserinfo(eq("1"), eq("profile"), eq("new value"))).thenReturn(rest);

        mockMvc.perform(post("/superAdmin/changeUserinfo")
                        .param("userId", "1")
                        .param("changeItem", "profile")
                        .param("changeVariable", "new value"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("修改成功")));
    }

    @Test
    void addUser() throws Exception {
        Rest rest = new Rest(200, "添加成功");
        when(superAdminServiceimp.faddUser(eq("newUser"), eq("role"), eq("info"))).thenReturn(rest);

        mockMvc.perform(post("/superAdmin/addUser")
                        .param("userName", "newUser")
                        .param("userRole", "role")
                        .param("userInfo", "info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("添加成功")));
    }

    @Test
    void deleteUser() throws Exception {
        Rest rest = new Rest(200, "删除成功");
        when(superAdminServiceimp.fdeleteUser("1")).thenReturn(rest);

        mockMvc.perform(post("/superAdmin/deleteUser")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("删除成功")));
    }
}
