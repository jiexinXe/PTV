package com.example.ptv.controller;


import com.example.ptv.entity.Car;
import com.example.ptv.service.CarService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car")
@CrossOrigin
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private KafkaTemplate<String ,Object> kafkaTemplate;
    @Autowired
    private Gson gson = new GsonBuilder().create();

    @GetMapping("/all")
    public List<Car> getAllCars() {
        return carService.getAllCars(); // 调用Service层获取所有小车信息
    }



    //测试用
    @GetMapping("/test")
    public List<Car> testCars() {
        kafkaTemplate.send("order-created",gson.toJson(666));
        return carService.getAllCars(); // 调用Service层获取所有小车信息
    }
}
