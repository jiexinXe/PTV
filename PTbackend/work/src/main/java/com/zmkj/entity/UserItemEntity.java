package com.zmkj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


//用户和他们对应的存储表
@Data
@TableName("User_Item")
public class UserItemEntity {
    @TableId
    private Integer uiId;
    //货物ID
    private Integer itemId;
    //用户ID
    private Integer userId;
    //存储时间
    private Date storetime;
    //操作时间
    private Date operatetime;
    //存储信息
    private String uiinfo;
}
