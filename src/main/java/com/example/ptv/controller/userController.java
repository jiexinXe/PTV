package com.example.ptv.controller;

import com.example.ptv.service.Imp.userServiceImp;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.sql.ClientInfoStatus;
import java.util.Map;

/**
 * 本Controller类用于处理与用户管理相关的请求
 *
 * */


@RestController
@RequestMapping("/user")
@CrossOrigin
public class userController {

    @Autowired
    private userServiceImp userServiceimp;


//    /**
//     * Param
//     * return:一个包含所有user类型的对象的列表
//     * 功能是获取全体用户的简略信息
//     * */
//    @GetMapping("/getallUserinfo")
//    public Rest getallUserinfo(){
//        return userServiceimp.getallUserinfo();
//    }

    /**
     * Param:userId:要查询信息的用户id
     * return:Rest类型对象，包含状态码，Data部分是User类型的对象
     * 用于查询某一特定用户的详细信息
     * */
    @GetMapping("/getUserinfo/{userId}")
    public Rest getUserinfo(@PathVariable("userId") Integer userId){
        return userServiceimp.getUserinfo(userId);
    }


    /**
     * Param:requestBody
     * return:Rest类型变量
     * 用于用户修改个人信息
     * */
    @PostMapping("/changeUserinfo")
    public Rest changeUserinfo(@RequestBody String requestBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode requestJson = mapper.readTree(requestBody);

        String userId = requestJson.get("userId").asText();
        String changeItem = requestJson.get("changeItem").asText();
        String changeVariableString = requestJson.get("changeVariable").asText();
        Map<String, Object> changeVariable = mapper.readValue(changeVariableString, new TypeReference<Map<String, Object>>(){});

        return userServiceimp.changeUserinfo(userId, changeItem, changeVariable);
    }





//    /**
//     * Param:userName:添加的用户的姓名;userRole:添加的用户的身份（个人，公司）;userInfo:添加的用户的其他信息（如果需要）
//     * return:Rest类型对象，包含状态码，Data部分为操作结果
//     * */
//    @PostMapping("/addUser")
//    public Rest addUser(@RequestParam("userName") String userName, @RequestParam("userRole") String userRole, @RequestParam("userInfo") String userInfo){
//        return userServiceimp.addUser(userName, userRole, userInfo);
//    }


//    /**
//     * Param:userId:用户对应的id
//     * return:Rest类型对象，包含状态码和操作结果
//     * 该功能用于删除数据库中的用户信息
//     * */
//    @PostMapping("/deleteUser")
//    public Rest deleteUser(@RequestParam("userId") String userId){
//        return userServiceimp.deleteUser(userId);
//    }


}
