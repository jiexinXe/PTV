package com.example.ptv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 该实体类对应数据库中的历史操作实体
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("History")
public class History {
    Integer id;
    Integer itemId;
    Integer userId;
    Date createTime;
    Integer modifyId;
    String modifyInfo;
}
