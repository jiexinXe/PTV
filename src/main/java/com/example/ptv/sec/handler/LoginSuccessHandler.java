package com.example.ptv.sec.handler;


import cn.hutool.json.JSONUtil;

import com.alibaba.fastjson.JSONObject;

import com.example.ptv.bean.ComUser;
import com.example.ptv.bean.Result;
import com.example.ptv.service.userService;
import com.example.ptv.utils.JwtUtils;
import com.example.ptv.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;
    @Resource
    RedisUtil redisUtils;

    @Autowired
    userService usersService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ComUser user = (ComUser) authentication.getPrincipal();
        System.out.println("再爱的勇气");
        System.out.println(user);
        System.out.println("可以还我了吗");
        String jwt = JwtUtils.generateToken(user);
        if (redisUtils.exists("login:" + user.getUserId())) {
            redisUtils.remove("login:" + user.getUserId());
        }

        redisUtils.set("login:" + user.getUserId(), jwt);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        response.setHeader("Authorization", jwt);



        Result result = Result.succ(usersService.getByName(user.getUsername()));

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();

    }

}

