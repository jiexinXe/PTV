package com.example.ptv.controller;

import com.example.ptv.dao.CargoDao;
import com.example.ptv.entity.Cargo;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.R;
import com.example.ptv.utils.Rest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "kafkaProducer")
public class KafkaProducerController {
    @Autowired
    private KafkaTemplate<String ,Object> kafkaTemplate;
    @Autowired
    public CargoDao cargodao;

    private Gson gson = new GsonBuilder().create();

    @GetMapping(value = "/sendMessage")
    public R sendMessage(){
        log.info("+++++++++++++++++++++  message = {}", gson.toJson("我是张凯歌大王"));
        kafkaTemplate.send("test",gson.toJson("我是张凯歌大王"));
        return  R.ok();
    }

    @GetMapping("/testMap")
    public Rest testMap(@RequestParam("cid")String cid){
        Cargo cargo = cargodao.selectById(cid);
        Map<String, Object> ans = new HashMap<>();
        ans.put("cargo", cargo);
        kafkaTemplate.send("cargotest",gson.toJson(ans));
        return new Rest(Code.rc200.getCode(),"测试进行中");
    }
}