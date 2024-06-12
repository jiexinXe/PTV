package com.example.ptv.controller;

import com.example.ptv.service.Imp.operationServiceImp;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class OperationControllerTest {

    @MockBean
    private operationServiceImp operationServiceImp;

    private MockMvc mockMvc;

    @InjectMocks
    private operationController operationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(operationController).build();
    }

    @Test
    void deposit() throws Exception {
        when(operationServiceImp.fdeposit(anyString(), anyString(), anyString())).thenReturn(new Rest(200, "存放成功"));

        mockMvc.perform(post("/deposit")
                        .param("itemName", "Item1")
                        .param("itemType", "TypeA")
                        .param("itemInfo", "Info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("存放成功")));
    }

    @Test
    void getItem() throws Exception {
        when(operationServiceImp.fgetItem(eq("1"))).thenReturn(new Rest(200, "取出成功"));

        mockMvc.perform(post("/getItem")
                        .param("itemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("取出成功")));
    }

    @Test
    void getAllitemInfo() throws Exception {
        when(operationServiceImp.fgetAllitemInfo(eq("1"))).thenReturn(new Rest(200, "获取所有货物信息成功"));

        mockMvc.perform(get("/getAllitemInfo")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("获取所有货物信息成功")));
    }

    @Test
    void getItemInfo() throws Exception {
        when(operationServiceImp.fgetItemInfo(eq("1"))).thenReturn(new Rest(200, "获取货物信息成功"));

        mockMvc.perform(get("/getItemInfo")
                        .param("itemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("获取货物信息成功")));
    }
}
