package com.example.ptv.controller;


import com.example.ptv.entity.Cargo;
import com.example.ptv.service.CargoService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        System.out.println(cargo);
        boolean success = cargoService.addCargo(cargo);
        if (success) {
            return new Rest(Code.rc200.getCode(),"添加货物成功");
        } else {
            return new Rest(Code.rc400.getCode(), "添加货物失败");
        }
    }


}
