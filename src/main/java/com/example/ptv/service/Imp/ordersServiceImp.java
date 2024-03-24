package com.example.ptv.service.Imp;


import com.example.ptv.dao.itemDao;
import com.example.ptv.dao.ordersDao;
import com.example.ptv.service.ordersService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * author：SH
 * 该类用于完成订单相关功能的实现
 * */
@Service

public class ordersServiceImp implements ordersService {

    @Autowired
    itemDao itemdao;
    @Autowired
    ordersDao ordersdao;

    public Rest addOrder(String itemName, Double numOfItem, String type, Integer userId, Date startTime, Date endTime){

        /**将订单包含的货物放进item数据表，并获取该货物的id*/
        itemdao.addItem(itemName, type, userId, endTime);
        List<Integer> itemidList = itemdao.selectItemidByUserid(userId);
        Integer itemId = itemidList.get(itemidList.size()-1);

        /**将订单信息放入info表，并获取id*/
        ordersdao.addOrderInfo(itemId, numOfItem, startTime, endTime, userId);
        List<Integer> infoIdList = ordersdao.selectIdByuserId(userId);
        Integer oinfo_id = infoIdList.get(infoIdList.size()-1);

        /**最终将info_id及其他信息放入orders表*/
        ordersdao.addOrder(oinfo_id, type);

        return new Rest(Code.rc200.getCode(), "存放完成");
    }

}
