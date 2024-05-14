package com.example.ptv.controller;

import com.example.ptv.service.Imp.ordersServiceImp;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrdersControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ordersServiceImp ordersserviceimp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addOrder() throws Exception {
        Rest rest = new Rest(Code.rc200.getCode(), "Order Added");

        when(ordersserviceimp.addOrder(any(String.class), any(Double.class), any(String.class), any(Integer.class), any(Date.class), any(Date.class))).thenReturn(rest);

        mockMvc.perform(post("/order/add")
                        .param("user_id", "1")
                        .param("name", "Order1")
                        .param("num", "10")
                        .param("type", "Type1")
                        .param("start_time", "2024-05-14")
                        .param("end_time", "2024-05-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Code").value(Code.rc200.getCode()))
                .andExpect(jsonPath("$.msg").value("Order Added"));
    }
}
