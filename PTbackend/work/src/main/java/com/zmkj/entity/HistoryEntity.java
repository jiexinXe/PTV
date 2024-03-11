package com.zmkj.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

//修改记录表
@Data
@TableName("History")
public class HistoryEntity {
    @TableId
    private Integer historyId;
    //货物ID
    private Integer itemId;
    //用户ID
    private Integer userId;
    //创建时间
    private Date createtime;
    //修改ID
    private Integer modifyid;
    //修改信息
    private String modifyinfo;

}
