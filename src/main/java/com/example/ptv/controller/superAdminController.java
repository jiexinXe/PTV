package com.example.ptv.controller;

import com.example.ptv.service.Imp.superAdminServiceImp;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.sql.ClientInfoStatus;

/**
 * 本Controller类用于处理与用户管理相关的请求
 * 仓库管理的请求处理不在这里
 *
 * */


@Controller
@RequestMapping("/superAdmin")
public class superAdminController {

    @Autowired
    private superAdminServiceImp superAdminServiceimp;


    /**
     * Param
     * return:一个包含所有user类型的对象的列表
     * 功能是获取全体用户的简略信息
     * */
    @GetMapping("/getallUserinfo")
    public Rest getallUserinfo(){
        return superAdminServiceimp.fgetallUserinfo();
    }

    /**
     * Param:userId:要查询信息的用户id
     * return:Rest类型对象，包含状态码，Data部分是User类型的对象
     * 用于查询某一特定用户的详细信息
     * */
    @GetMapping("/getUserinfo/{userId}")
    public Rest getUserinfo(@PathVariable("userId") String userId){
        return superAdminServiceimp.fgetUserinfo(userId);
    }


    /**
     * Param:userId:所要修改信息的用户的id; changeItem:所要修改的属性; changeVariable:修改后的值
     * return:Rest类型变量,包含状态码，Data部分是修改结果
     * 用于管理员修改用户信息
     * */
    @PostMapping("/changeUserinfo")
    public Rest changeUserinfo(@RequestParam("userId") String userId, @RequestParam("changeItem") String changeItem, @RequestParam("changeVariable") String changeVatiable){
        return superAdminServiceimp.fchangeUserinfo(userId, changeItem, changeVatiable);
    }


    /**
     * Param:userName:添加的用户的姓名;userRole:添加的用户的身份（个人，公司）;userInfo:添加的用户的其他信息（如果需要）
     * return:Rest类型对象，包含状态码，Data部分为操作结果
     * */
    @PostMapping("/addUser")
    public Rest addUser(@RequestParam("userName") String userName, @RequestParam("userRole") String userRole, @RequestParam("userInfo") String userInfo){
        return superAdminServiceimp.faddUser(userName, userRole, userInfo);
    }


    /**
     * Param:userId:用户对应的id
     * return:Rest类型对象，包含状态码和操作结果
     * 该功能用于删除数据库中的用户信息
     * */
    @PostMapping("/deleteUser")
    public Rest deleteUser(@RequestParam("userId") String userId){
        return superAdminServiceimp.fdeleteUser(userId);
    }


}
