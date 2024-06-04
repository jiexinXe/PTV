package com.example.ptv.controller;

import cn.hutool.db.sql.Order;
import com.example.ptv.dao.ordersDao;
import com.example.ptv.entity.orders;
import com.example.ptv.service.CarService;
import com.example.ptv.service.CargoService;
import com.example.ptv.service.Imp.CargoServiceImp;
import com.example.ptv.service.Imp.ordersServiceImp;
import com.example.ptv.service.ordersService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequestMapping(value = "kafkaConsumer")
public class ConsumerListener {
    @Autowired
    private ordersService ordersservice;
    @Autowired
    private CargoService cargoservice;
    @Autowired
    private ordersDao orderdao;

    @Autowired
    private CarService carService;
    private Gson gson = new GsonBuilder().create();

    @KafkaListener(topics = "cargo-info")
    public void consumeMessage(ConsumerRecord<?,?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("----------------- record =" + record);
            log.info("----------------- message =" + message);
            try {
                Integer cargoId = Integer.valueOf(message.toString());
                ordersservice.autoAddOrder(cargoId);
            } catch (NumberFormatException e) {
                log.error("Failed to convert message to Integer: " + message, e);
            }
        }
    }
    @KafkaListener(topics = "order-approved")
    public void consumeMessageOrderCreated(ConsumerRecord<?,?> record) {
        System.out.println("这里是车响应监听的地方");
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("----------------- record =" + record);
            log.info("----------------- message =" + message);
            try {
                Integer orderId = Integer.valueOf(message.toString());
                orders order = orderdao.selectById(String.valueOf(orderId));
                order.setStates("运输中");
                orderdao.updateById(order);
                System.out.println("这里是车");
                carService.processOrder(orderId);
            } catch (NumberFormatException e) {
                log.error("Failed to convert message to Integer: " + message, e);
            }
        }

    }

    @KafkaListener(topics = "car-processing")
    public void consumeMessageShelfUpdated(ConsumerRecord<?,?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("----------------- record =" + record);
            log.info("----------------- message =" + message);

        }
    }

    @KafkaListener(topics = "cargo-remove")
    public void cargoBeRemoved(ConsumerRecord<?,?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("----------------- record =" + record);
            log.info("----------------- message =" + message);
            System.out.println("有货物要被拿走了");
            System.out.println("被拿走的货物是"+message.toString().split(";")[0]+"数量是"+message.toString().split(";")[1]);
            try {
                String cargoId = message.toString();
                String[] strings = cargoId.split(";");
                String cid = strings[0];
                String num = strings[1];
                System.out.println("货物"+cid+"数量"+num);
//                cargoservice.deleteCargo(cid, num);
            } catch (NumberFormatException e) {
                log.error("Failed to convert message to Integer: " + message, e);
            }
        }
    }
}
