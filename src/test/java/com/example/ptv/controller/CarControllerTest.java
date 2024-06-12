package com.example.ptv.controller;

import com.example.ptv.entity.Car;
import com.example.ptv.service.CarService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void testGetAllCars() throws Exception {
        Car car1 = new Car(1, 1, "Location1", "Task1");
        Car car2 = new Car(2, 2, "Location2", "Task2");
        List<Car> cars = Arrays.asList(car1, car2);

        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/car/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is(1)))
                .andExpect(jsonPath("$[0].location", is("Location1")))
                .andExpect(jsonPath("$[0].currentTask", is("Task1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].status", is(2)))
                .andExpect(jsonPath("$[1].location", is("Location2")))
                .andExpect(jsonPath("$[1].currentTask", is("Task2")));

        verify(carService, times(1)).getAllCars();
    }

    @Test
    void testTestCars() throws Exception {
        Car car1 = new Car(1, 1, "Location1", "Task1");
        Car car2 = new Car(2, 2, "Location2", "Task2");
        List<Car> cars = Arrays.asList(car1, car2);

        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/car/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is(1)))
                .andExpect(jsonPath("$[0].location", is("Location1")))
                .andExpect(jsonPath("$[0].currentTask", is("Task1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].status", is(2)))
                .andExpect(jsonPath("$[1].location", is("Location2")))
                .andExpect(jsonPath("$[1].currentTask", is("Task2")));

        verify(carService, times(1)).getAllCars();
        verify(kafkaTemplate, times(1)).send("order-created", new Gson().toJson(666));
    }
}
