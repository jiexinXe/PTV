package com.example.ptv.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 该实体类对应数据库中的用户数据表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    //用户ID
    Integer id;
    //用户姓名
    String name;
    //用户角色
    Integer role;
    //用户名
    String username;
    //用户密码
    String password;
    //性别
    String gender;
    //地址
    String address;
    //电子邮箱
    String email;
    //电话
    String phone;
    //上次登录时间
    Date lastLogin;

}
