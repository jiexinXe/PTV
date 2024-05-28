package com.example.ptv.controller;


import com.example.ptv.service.Imp.ordersServiceImp;
import com.example.ptv.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 该类用于处理订单相关的请求
 * */

@RestController
@RequestMapping("/order")
public class ordersController {

    @Autowired
    private ordersServiceImp ordersserviceimp;


    /**
     * author:sh
     * param：name:订单名称;type:存放货物类型;start_time:开始存放日期;end_time:存放截至日期
     * return:订单生成情况
     * */
    @PostMapping("/add")
    public Rest addOrder(@RequestParam("user_id") String user_id, @RequestParam("name") String name, @RequestParam("num")String num ,@RequestParam("type") String type, @RequestParam("start_time")String start_time, @RequestParam("end_time")String end_time) throws ParseException {


        /**将接口传过来的String类型的日期转化为Date类型*/
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Double numOfItem = Double.valueOf(num);
        Date starttime = ft.parse(start_time);
        Date endtime = ft.parse(end_time);
        Integer userId = Integer.valueOf(user_id);

        return ordersserviceimp.addOrder(name, numOfItem, type, userId, starttime,endtime);
    }

    @GetMapping("/list")
    public Rest getOrders(@RequestParam("userid")String userid){
        return ordersserviceimp.getOrdersByuserid(userid);
    }
}
