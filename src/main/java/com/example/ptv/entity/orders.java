package com.example.ptv.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("oeders")
public class orders {

    Integer id;//订单自己的id
    Integer oinfoId;//订单对应的货物信息id
    String states;//订单状态，包括（进行中，完成，中止）
    Date opreationId;//订单产生的时间

}
