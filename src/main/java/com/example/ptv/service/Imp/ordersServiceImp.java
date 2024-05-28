package com.example.ptv.service.Imp;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.ptv.dao.CargoDao;
import com.example.ptv.dao.itemDao;
import com.example.ptv.dao.orderinfoDao;
import com.example.ptv.dao.ordersDao;
import com.example.ptv.entity.Cargo;
import com.example.ptv.entity.order_use;
import com.example.ptv.entity.orderInfo;
import com.example.ptv.entity.orders;
import com.example.ptv.service.ordersService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
    @Autowired
    private CargoDao cargoDao;
    @Autowired
    private orderinfoDao infodao;
    @Autowired
    private KafkaTemplate<String ,Object> kafkaTemplate;
    @Autowired
    private Gson gson = new GsonBuilder().create();

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

    /**
     * 根据消息1生成订单
     * @param cargoId
     */
    @Override
    public void autoAddOrder(Integer cargoId){

        QueryWrapper<Cargo> cargowrapper = new QueryWrapper<>();
        cargowrapper.eq("cid",cargoId);
        Cargo cargo = cargoDao.selectOne(cargowrapper);
        /**订单创建过程中，货物状态改为1，意为货物审核中*/
        cargo.setStatus(1);

        cargoDao.updateById(cargo);

        orderInfo orderInfo = new orderInfo();
        orderInfo.setItemId(cargo.getCid());
        orderInfo.setNumOfItem( cargo.getNum());
        orderInfo.setStartTime(new Date());  // 假设为当前时间
        orderInfo.setEndTime(new Date());    // 假设为当前时间
        orderInfo.setUserId(cargo.getUserid());

        ordersdao.autoAddOrderInfo(orderInfo);

        System.out.println("Generated Order Info ID: " + orderInfo.getId());

        String type="do not why";
        orders orders = new orders();
        orders.setOinfoId(orderInfo.getId());
        orders.setStates("待审批");

        /**最终将info_id及其他信息放入orders表*/
        /**默认订单添加必然成功*/
        ordersdao.autoAddOrder(orders);

        QueryWrapper<Cargo> cargowrapper_new = new QueryWrapper<>();
        cargowrapper.eq("cid",cargoId);
        Cargo cargo_new = cargoDao.selectOne(cargowrapper);
        /**订单生成完成后货物状态改为审核完成*/
        cargo_new.setStatus(2);
        cargoDao.updateById(cargo_new);

        System.out.println(orders.getId());
        System.out.println("订单创建完成，但未审批");
        kafkaTemplate.send("order-created",gson.toJson(orders.getId()));

        return ;
    }

    @Override
    public Rest getOrdersByuserid(String userid){
        List<order_use> ordersList = ordersdao.getOrdersByUserid(userid);
        return new Rest(Code.rc200.getCode(), ordersList, "用户所有订单");
    }

    @Override
    public Rest approve(String oid){
        orders orders = ordersdao.selectById(oid);
        orders.setStates("已审核");
        System.out.println("订单已经审批，准备召唤车车");
        kafkaTemplate.send("order-approved",gson.toJson(orders.getId()));

        return new Rest(Code.rc200.getCode(), "审批完成");
    }
}
