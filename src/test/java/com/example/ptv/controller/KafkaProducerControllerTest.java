package com.example.ptv.controller;

import com.example.ptv.dao.CargoDao;
import com.example.ptv.entity.Cargo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"test", "cargotest"})
public class KafkaProducerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @MockBean
    private CargoDao cargoDao;

    private Gson gson = new GsonBuilder().create();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testSendMessage() throws Exception {
        mockMvc.perform(get("/kafkaProducer/sendMessage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)));
    }

    @Test
    void testTestMap() throws Exception {
        Cargo cargo = new Cargo();
        cargo.setCid(1);
        when(cargoDao.selectById(anyString())).thenReturn(cargo);

        mockMvc.perform(get("/kafkaProducer/testMap")
                        .param("cid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.msg", is("测试进行中")));
    }
}
