package com.example.ptv.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Component
@RequestMapping(value = "kafkaConsumer")
public class ConsumerListener {
    @KafkaListener(topics = "test")
    public void consumeMessage(ConsumerRecord<?,?> record){
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()){
            Object message = kafkaMessage.get();
            log.info("----------------- record =" + record);
            log.info("----------------- message =" + message);
        }
    }
}
