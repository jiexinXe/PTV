//package com.example.ptv.controller;
//
//import com.example.ptv.entity.Cargo;
//import com.example.ptv.service.CargoService;
//import com.example.ptv.utils.Code;
//import com.example.ptv.utils.Rest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//class CargoControllerTest {
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CargoService cargoService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @WithMockUser
//    @Test
//    void addCargo() throws Exception {
//        Cargo cargo = new Cargo();
//        when(cargoService.addCargo(any(Cargo.class), any(String.class))).thenReturn(true);
//
//        mockMvc.perform(post("/cargo/add")
//                        .param("userid", "1")
//                        .contentType("application/json")
//                        .content("{\"name\": \"cargoName\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(Code.rc200.getCode()))
//                .andExpect(jsonPath("$.msg").value("添加货物成功"));
//    }
//
//    @WithMockUser
//    @Test
//    void updateCargo() throws Exception {
//        Cargo cargo = new Cargo();
//        when(cargoService.updateCargo(any(Cargo.class))).thenReturn(true);
//
//        mockMvc.perform(post("/cargo/update")
//                        .contentType("application/json")
//                        .content("{\"name\": \"cargoName\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(Code.rc200.getCode()))
//                .andExpect(jsonPath("$.msg").value("更新成功"));
//    }
//
//    @WithMockUser
//    @Test
//    void deleteCargo() throws Exception {
//        when(cargoService.deleteCargo(eq("1"), eq("10"))).thenReturn(new Rest(Code.rc200.getCode(), "删除成功"));
//
//        mockMvc.perform(delete("/cargo/delete")
//                        .param("id", "1")
//                        .param("num", "10"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(Code.rc200.getCode()))
//                .andExpect(jsonPath("$.msg").value("删除成功"));
//    }
//
//    @WithMockUser
//    @Test
//    void getCargoListByUserId() throws Exception {
//        when(cargoService.getCargoListByUserId(eq("user1"))).thenReturn(new Rest(Code.rc200.getCode(), "获取成功"));
//
//        mockMvc.perform(get("/cargo/list/userid")
//                        .param("userid", "user1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(Code.rc200.getCode()))
//                .andExpect(jsonPath("$.msg").value("获取成功"));
//    }
//
//    @WithMockUser
//    @Test
//    void getOneCargo() throws Exception {
//        Cargo cargo = new Cargo();
//        when(cargoService.getCargoById(eq("1"))).thenReturn(cargo);
//
//        mockMvc.perform(get("/cargo/one")
//                        .param("cid", "1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(Code.rc200.getCode()))
//                .andExpect(jsonPath("$.data.cargo").exists())
//                .andExpect(jsonPath("$.msg").value("货物信息"));
//    }
//}
