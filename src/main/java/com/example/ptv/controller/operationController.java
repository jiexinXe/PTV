package com.example.ptv.controller;


import com.example.ptv.service.Imp.operationServiceImp;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个类主要用于定义一些用户操作的请求
 * 例如，存放货物，取出货物等
 * */
@RestController
public class operationController {


    @Autowired
    private operationServiceImp operationServiceimp;
    /**
     * Param:name:货物名称; type:货物类型; info:货物其他信息
     * return: 操作结果
     * 该功能用于用户存放货物
     * */
    @PostMapping("/deposit")
    public Rest deposit(@RequestParam("itemName") String name, @RequestParam("itemType") String type, @RequestParam("itemInfo") String info){
        return operationServiceimp.fdeposit(name, type, info);
    }

    /**
     * Param:id:货物对应的id
     * return:对应货物的实体对象，以及操作结果
     * 该功能用于客户取出单个货物
     * 在返回信息的同时，会执行货物的删除操作
     * */
    @PostMapping("/getItem")
    public Rest getItem(@RequestParam("itemId") String id){
        return operationServiceimp.fgetItem(id);
    }

    /**
     * Param:id:用户的id
     * return:该用户所有货物的信息
     * 该功能用于用户查询自己存放的所有货物的列表
     * */
    @GetMapping("/getAllitemInfo")
    public Rest getAllitemInfo(@RequestParam("userId") String id){
        return operationServiceimp.fgetAllitemInfo(id);
    }


    /**
     * Param:id:货物的id
     * return:id对应的货物的实体对象
     * 该功能用于货物货物的详细信息
     * */
    @GetMapping("/getItemInfo")
    public Rest getItemInfo(@RequestParam("itemId") String id){
        return operationServiceimp.fgetItemInfo(id);
    }
}