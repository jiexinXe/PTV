package com.example.ptv.controller;

import com.example.ptv.entity.ShelvesEntity;
import com.example.ptv.service.ShelvesService;
import com.example.ptv.utils.PageUtils;
import com.example.ptv.utils.Rest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class ShelvesControllerTest {

    @MockBean
    private ShelvesService shelvesService;

    private MockMvc mockMvc;

    @InjectMocks
    private ShelvesController shelvesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shelvesController).build();
    }

    @Test
    void list() throws Exception {
        PageUtils page = new PageUtils(Collections.emptyList(), 0, 10, 1);
        when(shelvesService.queryPage(anyMap())).thenReturn(page);

        mockMvc.perform(get("/authserver/shelves/list")
                        .param("page", "1")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalCount", is(0)))
                .andExpect(jsonPath("$.page.pageSize", is(10)))
                .andExpect(jsonPath("$.page.currPage", is(1)));
    }

    @Test
    void info() throws Exception {
        ShelvesEntity shelves = new ShelvesEntity();
        shelves.setId(1);
        when(shelvesService.getById(anyInt())).thenReturn(shelves);

        mockMvc.perform(get("/authserver/shelves/info/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shelves.id", is(1)));
    }

    @Test
    void save() throws Exception {
        mockMvc.perform(post("/authserver/shelves/save")
                        .contentType("application/json")
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(put("/authserver/shelves/update")
                        .contentType("application/json")
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/authserver/shelves/delete")
                        .contentType("application/json")
                        .content("[1, 2, 3]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)));
    }

    @Test
    void addShelves() throws Exception {
        when(shelvesService.addShelves(anyInt(), anyInt(), anyString())).thenReturn(new Rest(200, "添加成功"));

        mockMvc.perform(post("/authserver/shelves/add")
                        .param("num_column", "3")
                        .param("num_row", "4")
                        .param("warehouse_id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("添加成功")));
    }

    @Test
    void initShelves() throws Exception {
        when(shelvesService.add()).thenReturn(new Rest(200, "初始化成功"));

        mockMvc.perform(get("/authserver/shelves/init"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("初始化成功")));
    }

    @Test
    void getShelvesOfWarehouse() throws Exception {
        when(shelvesService.getInfo(anyString(), anyString())).thenReturn(new Rest(200, "获取成功"));

        mockMvc.perform(get("/authserver/shelves/info")
                        .param("warehouse_id", "1")
                        .param("shelve_id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("获取成功")));
    }

    @Test
    void getCargoOfShelve() throws Exception {
        when(shelvesService.getCarogoOfShelve(anyString(), anyString(), anyString(), anyString())).thenReturn(new Rest(200, "获取成功"));

        mockMvc.perform(get("/authserver/shelves/cargo")
                        .param("warehouse_id", "1")
                        .param("shelve_id", "1")
                        .param("row", "1")
                        .param("column", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("获取成功")));
    }
}
