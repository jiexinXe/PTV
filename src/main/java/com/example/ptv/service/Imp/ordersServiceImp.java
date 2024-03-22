package com.example.ptv.service.Imp;


import com.example.ptv.dao.ordersDao;
import com.example.ptv.service.ordersService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * author：SH
 * 该类用于完成订单相关功能的实现
 * */
@Service

public class ordersServiceImp implements ordersService {

    @Autowired
    ordersDao ordersdao;

    public Rest addOrder(String user_id, String name, String itemtype, Date start_time, Date end_time){

        if(name == null)
            return new Rest(Code.rc400.getCode(), "订单名称不存在");
        if(itemtype == null)
            return new Rest(Code.rc400.getCode(), "货物类型不存在");
        if(start_time == null || end_time == null)
            return new Rest(Code.rc400.getCode(), "存放时间不存在");

        ordersdao.addOrder(user_id, name, itemtype, start_time, end_time);

        return new Rest(Code.rc200.getCode(), "订单生成成功");
    }
}
