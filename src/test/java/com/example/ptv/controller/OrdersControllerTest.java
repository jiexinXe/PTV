package com.example.ptv.controller;

import com.example.ptv.service.Imp.ordersServiceImp;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class OrdersControllerTest {

    @MockBean
    private ordersServiceImp ordersServiceImp;

    private MockMvc mockMvc;

    @InjectMocks
    private ordersController ordersController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ordersController).build();
    }

    @Test
    void addOrder() throws Exception {
        when(ordersServiceImp.addOrder(any(), any(), any(), any(), any(), any())).thenReturn(new Rest(200, "订单生成成功"));

        mockMvc.perform(post("/order/add")
                        .param("user_id", "1")
                        .param("name", "Test Order")
                        .param("num", "10")
                        .param("type", "Type A")
                        .param("start_time", "2024-06-01")
                        .param("end_time", "2024-06-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("订单生成成功")));
    }

    @Test
    void getOrders() throws Exception {
        when(ordersServiceImp.getOrdersByuserid("1")).thenReturn(new Rest(200, "订单列表"));

        mockMvc.perform(get("/order/list")
                        .param("userid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("订单列表")));
    }

    @Test
    void approveOrder() throws Exception {
        when(ordersServiceImp.approve("1")).thenReturn(new Rest(200, "订单审批成功"));

        mockMvc.perform(post("/order/approve")
                        .param("oid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("订单审批成功")));
    }
}
