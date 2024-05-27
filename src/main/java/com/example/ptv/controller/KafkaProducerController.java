package com.example.ptv.controller;

import com.example.ptv.utils.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "kafkaProducer")
public class KafkaProducerController {
    @Autowired
    private KafkaTemplate<String ,Object> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    @GetMapping(value = "/sendMessage")
    public R sendMessage(){
        log.info("+++++++++++++++++++++  message = {}", gson.toJson("我是张凯歌大王"));
        kafkaTemplate.send("test",gson.toJson("我是张凯歌大王"));
        return  R.ok();
    }
}