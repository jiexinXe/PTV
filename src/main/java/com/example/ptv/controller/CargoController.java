package com.example.ptv.controller;


import com.example.ptv.entity.Cargo;
import com.example.ptv.service.CargoService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 本Controller类用于处理与货物管理相关的请求
 *
 * */


@RestController
@RequestMapping("/cargo")
@CrossOrigin
public class CargoController {
    @Autowired
    private CargoService cargoService;
    @Autowired
    private KafkaTemplate<String ,Object> kafkaTemplate;
    @Autowired
    private Gson gson = new GsonBuilder().create();

    @PostMapping("/add")
    public Rest addCargo(@RequestBody Cargo cargo, @RequestParam("userid")String userid) {
        System.out.println("用户"+userid+"在添加货物");
        boolean success = cargoService.addCargo(cargo, userid);
        if (success) {
            System.out.println("这里是货物添加成功的输出值");
            return new Rest(Code.rc200.getCode(),"添加货物成功");
        } else {
            return new Rest(Code.rc400.getCode(), "添加货物失败");
        }
    }
    /**
     * 更新货物信息
     * 需要上流将cargo对象的数据以body的方式传过来
     * */
    @PostMapping("/update")
    public Rest update(@RequestBody Cargo cargo){
        System.out.println(cargo);
        boolean success = cargoService.updateCargo(cargo);
        if(success)
            return new Rest(Code.rc200.getCode(), "更新成功");
        return new Rest(Code.rc400.getCode(), "更新失败");
    }
    /**
     * 提取货物的接口
     * */
    @DeleteMapping("/delete")
    public Rest deleteCargo(@RequestParam("id") String cid, @RequestParam("num")String num){
        String message = cid+";"+num;
        System.out.println("提取货物开始");
//        kafkaTemplate.send("cargo-remove", gson.toJson(message));
        cargoService.deleteCargo(cid,num);
        return new Rest(Code.rc200.getCode(), "删除成功");
    }
    /**
     * 获取某一个用户的所有货物
     * */
//    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    @GetMapping("/list/userid")
    public Rest getCargoListByUserId(@RequestParam("userid")String id){
        System.out.println(cargoService.getCargoListByUserId(id));
        return cargoService.getCargoListByUserId(id);
    }
    @GetMapping("/one")
    public Rest getOneCargo(@RequestParam("cid")String cid){

        Cargo cargo = cargoService.getCargoById(cid);
        Map<Object, Object> ans = new HashMap<>();
        ans.put("cargo", cargo);
        return new Rest(Code.rc200.getCode(), ans, "货物信息");
    }
}
