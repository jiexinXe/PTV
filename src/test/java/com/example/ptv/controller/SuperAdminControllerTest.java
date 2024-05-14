package com.example.ptv.controller;

import com.example.ptv.service.Imp.superAdminServiceImp;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SuperAdminControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private superAdminServiceImp superAdminServiceimp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getallUserinfo() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "All Users Info");

        when(superAdminServiceimp.fgetallUserinfo()).thenReturn(rest);

        mockMvc.perform(get("/superAdmin/getallUserinfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("All Users Info"));
    }

    @Test
    void getUserinfo() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "User Info");

        when(superAdminServiceimp.fgetUserinfo(eq("1"))).thenReturn(rest);

        mockMvc.perform(get("/superAdmin/getUserinfo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("User Info"));
    }

    @Test
    void changeUserinfo() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "User Info Changed");

        when(superAdminServiceimp.fchangeUserinfo(any(String.class), any(String.class), any(String.class))).thenReturn(rest);

        mockMvc.perform(post("/superAdmin/changeUserinfo")
                        .param("userId", "1")
                        .param("changeItem", "name")
                        .param("changeVariable", "newName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("User Info Changed"));
    }

    @Test
    void addUser() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "User Added");

        when(superAdminServiceimp.faddUser(any(String.class), any(String.class), any(String.class))).thenReturn(rest);

        mockMvc.perform(post("/superAdmin/addUser")
                        .param("userName", "testUser")
                        .param("userRole", "testRole")
                        .param("userInfo", "testInfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("User Added"));
    }

    @Test
    void deleteUser() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "User Deleted");

        when(superAdminServiceimp.fdeleteUser(any(String.class))).thenReturn(rest);

        mockMvc.perform(post("/superAdmin/deleteUser")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("User Deleted"));
    }
}
