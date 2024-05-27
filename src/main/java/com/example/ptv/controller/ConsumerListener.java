package com.example.ptv.controller;

import com.example.ptv.service.CarService;
import com.example.ptv.service.Imp.ordersServiceImp;
import com.example.ptv.service.ordersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Component
@RequestMapping(value = "kafkaConsumer")
public class ConsumerListener {
    @Autowired
    private ordersService ordersservice;

    @Autowired
    private CarService carService;
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
    @KafkaListener(topics = "order-created")
    public void consumeMessageOrderCreated(ConsumerRecord<?,?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("----------------- record =" + record);
            log.info("----------------- message =" + message);
            try {
                Integer orderId = Integer.valueOf(message.toString());
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

}
