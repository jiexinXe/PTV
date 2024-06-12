package com.example.ptv.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("orders")
public class orders {
    @TableId(type = IdType.AUTO)
    Integer id;//订单自己的id
    Integer oinfoId;//订单对应的货物信息id
    String states;//订单状态，包括（进行中，完成，中止）
    String type;
    Date operationTime;//订单产生的时间

}
