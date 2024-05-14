package com.example.ptv.controller;

import com.example.ptv.service.Imp.operationServiceImp;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OperationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private operationServiceImp operationServiceimp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void deposit() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "Deposit Success");

        when(operationServiceimp.fdeposit(any(String.class), any(String.class), any(String.class))).thenReturn(rest);

        mockMvc.perform(post("/deposit")
                        .param("itemName", "item1")
                        .param("itemType", "type1")
                        .param("itemInfo", "info1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("Deposit Success"));
    }

    @Test
    void getItem() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "Item Retrieved");

        when(operationServiceimp.fgetItem(any(String.class))).thenReturn(rest);

        mockMvc.perform(post("/getItem")
                        .param("itemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("Item Retrieved"));
    }

    @Test
    void getAllitemInfo() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "All Items Info");

        when(operationServiceimp.fgetAllitemInfo(any(String.class))).thenReturn(rest);

        mockMvc.perform(get("/getAllitemInfo")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("All Items Info"));
    }

    @Test
    void getItemInfo() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "Item Info");

        when(operationServiceimp.fgetItemInfo(any(String.class))).thenReturn(rest);

        mockMvc.perform(get("/getItemInfo")
                        .param("itemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("Item Info"));
    }
}
