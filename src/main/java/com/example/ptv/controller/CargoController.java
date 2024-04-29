package com.example.ptv.controller;


import com.example.ptv.entity.Cargo;
import com.example.ptv.service.CargoService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.PageUtils;
import com.example.ptv.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/add")
    public Rest addCargo(@RequestBody Cargo cargo) {
        boolean success = cargoService.addCargo(cargo);
        if (success) {
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
        System.out.println(cid);
        return cargoService.deleteCargo(cid,num);
    }

    /**
     * 获取某一个用户的所有货物
     * */
    @GetMapping("/list/userid")
    public Rest getCargoListByUserId(@RequestParam("userid")String id){
        return cargoService.getCargoListByUserId(id);
    }

    @GetMapping("/one")
    public Rest getOneCargo(@RequestParam("cid")String cid){

        Cargo cargo = cargoService.getCargoById(cid);
        Map<Object, Object> ans = new HashMap<>();
        ans.put("cargo", cargo);
        return new Rest(Code.rc200.getCode(), ans, "货物信息");
    }

    /**
     * 分页查询接口
     * params中传入页数和每页记录数(page,limit)
     */
    @RequestMapping("/list")
    public Rest list(@RequestParam Map<String, Object> params){
        PageUtils page = cargoService.queryPage(params);

        return new Rest(Code.rc200.getCode(),page, "货物信息");
    }

}
