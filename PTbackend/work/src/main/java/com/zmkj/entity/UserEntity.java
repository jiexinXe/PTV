package com.zmkj.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


//用户表
@Data
@TableName("User")
public class UserEntity {


    //用户ID
    @TableId
    private Integer userId;
    //用户角色：0 超级管理员 ； 1 仓库管理员 ； 2 普通用户
    private Integer userrole;
    //用户名
    private String username;
    //用户信息
    private String userinfo;
}
