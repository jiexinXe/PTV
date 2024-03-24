package com.example.ptv.controller;

import com.example.ptv.service.Imp.loginServiceImp;
import com.example.ptv.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginController {

    @Autowired
    private loginServiceImp loginServiceimp;

    /**
     * Param:userId:用户id; passWord:用户密码
     * return: Rest类型对象，包含状态码与登录结果
     * */
    @PostMapping("/login")
    public Rest checklogin(@RequestParam("username") String userId, @RequestParam("password") String passWord){
        return loginServiceimp.fchecklogin(userId, passWord);
    }


    /**
     * Param:userName:用户注册时使用的名字； passWord:用户设置的密码; userRole:用户的身份（个人，公司）
     * return:Rest类型对象，包含状态码和注册结果
     * */
    @PostMapping("/register")
    public Rest register(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord, @RequestParam("userRole") String userRole){
        return loginServiceimp.fregister(userName, passWord, userRole);
    }


}
