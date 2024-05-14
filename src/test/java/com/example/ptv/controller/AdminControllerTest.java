package com.example.ptv.controller;

import com.example.ptv.service.Imp.adminServiceImp;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private adminServiceImp adminserviceimp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getListofWH() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "List of WH");

        when(adminserviceimp.fgetListofWH()).thenReturn(rest);

        mockMvc.perform(get("/admin/listOfWH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("List of WH"));
    }

    @Test
    void getWHinfo() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "WH info");

        when(adminserviceimp.fgetWHinfo("1")).thenReturn(rest);

        mockMvc.perform(get("/admin/getWHinfo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("WH info"));
    }

    @Test
    void changeWH() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "Change WH");

        when(adminserviceimp.fchangeWH(any(String.class), any(String.class), any(String.class))).thenReturn(rest);

        mockMvc.perform(post("/admin/changeWH")
                        .param("WHId", "1")
                        .param("changeItem", "type")
                        .param("changeVariable", "newType"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("Change WH"));
    }

    @Test
    void deleteWH() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "Delete WH");

        when(adminserviceimp.fdeleteWH(any(String.class))).thenReturn(rest);

        mockMvc.perform(post("/admin/deleteWH")
                        .param("WHId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("Delete WH"));
    }

    @Test
    void addWH() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "Add WH");

        when(adminserviceimp.faddWH(any(String.class), any(String.class), any(String.class))).thenReturn(rest);

        mockMvc.perform(post("/admin/addWH")
                        .param("WHtype", "type")
                        .param("WHmaxcontent", "100")
                        .param("WHnowcontent", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("Add WH"));
    }
}
