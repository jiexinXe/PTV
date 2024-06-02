package com.example.ptv.controller;

import com.example.ptv.service.Imp.adminServiceImp;
import com.example.ptv.utils.Rest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyString;
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
        when(adminserviceimp.fgetListofWH()).thenReturn(new Rest(200, "获取成功"));

        mockMvc.perform(get("/admin/listOfWH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("获取成功"));
    }

    @Test
    void getWHinfo() throws Exception {
        when(adminserviceimp.fgetWHinfo(anyString())).thenReturn(new Rest(200, "获取成功"));

        mockMvc.perform(get("/admin/getWHinfo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("获取成功"));
    }

    @Test
    void changeWH() throws Exception {
        when(adminserviceimp.fchangeWH(anyString(), anyString(), anyString())).thenReturn(new Rest(200, "修改成功"));

        mockMvc.perform(post("/admin/changeWH")
                        .param("WHId", "1")
                        .param("changeItem", "type")
                        .param("changeVariable", "value"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("修改成功"));
    }

    @Test
    void deleteWH() throws Exception {
        when(adminserviceimp.fdeleteWH(anyString())).thenReturn(new Rest(200, "删除成功"));

        mockMvc.perform(post("/admin/deleteWH")
                        .param("WHId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("删除成功"));
    }

    @Test
    void addWH() throws Exception {
        when(adminserviceimp.faddWH(anyString(), anyString(), anyString())).thenReturn(new Rest(200, "添加成功"));

        mockMvc.perform(post("/admin/addWH")
                        .param("WHtype", "type")
                        .param("WHmaxcontent", "100")
                        .param("WHnowcontent", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("添加成功"));
    }
}
