package com.example.ptv.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.ptv.entity.order_use;
import com.example.ptv.service.Imp.ordersServiceImp;
import com.example.ptv.dao.CargoDao;
import com.example.ptv.dao.itemDao;
import com.example.ptv.dao.orderinfoDao;
import com.example.ptv.dao.ordersDao;
import com.example.ptv.entity.Cargo;
import com.example.ptv.entity.orderInfo;
import com.example.ptv.entity.orders;
import com.example.ptv.service.ordersService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OrdersServiceImpTest {

    @Mock
    private itemDao itemdao;

    @Mock
    private ordersDao ordersdao;

    @Mock
    private CargoDao cargoDao;

    @Mock
    private orderinfoDao infodao;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private ordersServiceImp ordersServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrder() {
        String itemName = "item1";
        Double numOfItem = 10.0;
        String type = "type1";
        Integer userId = 1;
        Date startTime = new Date();
        Date endTime = new Date();

        List<Integer> itemidList = Arrays.asList(1);
        List<Integer> infoIdList = Arrays.asList(1);

        when(itemdao.selectItemidByUserid(userId)).thenReturn(itemidList);
        when(ordersdao.selectIdByuserId(userId)).thenReturn(infoIdList);

        Rest result = ordersServiceImp.addOrder(itemName, numOfItem, type, userId, startTime, endTime);

        verify(itemdao, times(1)).addItem(eq(itemName), eq(type), eq(userId), eq(endTime));
        verify(ordersdao, times(1)).addOrderInfo(eq(itemidList.get(0)), eq(numOfItem), eq(startTime), eq(endTime), eq(userId));
        verify(ordersdao, times(1)).addOrder(eq(infoIdList.get(0)), eq(type));

        assert result.getCode() == Code.rc200.getCode();
        assert "存放完成".equals(result.getMsg());
    }

    @Test
    void testAutoAddOrder() {
        Integer cargoId = 1;
        Cargo cargo = new Cargo();
        cargo.setCid(cargoId);
        cargo.setNum(10);
        cargo.setUserid(1);

        when(cargoDao.selectOne(any(QueryWrapper.class))).thenReturn(cargo);

        orderInfo orderInfo = new orderInfo();
        orderInfo.setId(1);

        orders order = new orders();
        order.setId(1);

        doAnswer(invocation -> {
            orderInfo.setId(1);
            return null;
        }).when(ordersdao).autoAddOrderInfo(any(orderInfo.class));

        doAnswer(invocation -> {
            order.setId(1);
            return null;
        }).when(ordersdao).autoAddOrder(any(orders.class));

        ordersServiceImp.autoAddOrder(cargoId);

        verify(cargoDao, times(1)).selectOne(any(QueryWrapper.class));
        verify(cargoDao, times(1)).updateById(eq(cargo));
        verify(ordersdao, times(1)).autoAddOrderInfo(any(orderInfo.class));
        verify(ordersdao, times(1)).autoAddOrder(any(orders.class));
        verify(kafkaTemplate, times(1)).send(eq("order-created"), any(String.class));
    }

    @Test
    void testGetOrdersByuserid() {
        String userid = "1";
        List<order_use> ordersList = Arrays.asList(new order_use());

        when(ordersdao.getOrdersByUserid(userid)).thenReturn(ordersList);

        Rest result = ordersServiceImp.getOrdersByuserid(userid);

        verify(ordersdao, times(1)).getOrdersByUserid(eq(userid));
        assert result.getCode() == Code.rc200.getCode();
        assert result.getData().equals(ordersList);
        assert "用户所有订单".equals(result.getMsg());
    }

    @Test
    void testApprove() {
        String oid = "1";
        orders order = new orders();
        order.setId(1);
        order.setStates("待审批");

        Cargo cargo = new Cargo();
        cargo.setCid(1);
        cargo.setStatus(1);

        when(ordersdao.selectById(oid)).thenReturn(order);
        when(ordersdao.getCargoId(oid)).thenReturn("1");
        when(cargoDao.selectById("1")).thenReturn(cargo);

        Rest result = ordersServiceImp.approve(oid);

        verify(ordersdao, times(1)).selectById(eq(oid));
        verify(ordersdao, times(1)).getCargoId(eq(oid));
        verify(cargoDao, times(1)).selectById(eq("1"));
        verify(cargoDao, times(1)).updateById(eq(cargo));
        verify(ordersdao, times(1)).updateById(eq(order));
        verify(kafkaTemplate, times(1)).send(eq("order-approved"), any(String.class));

        assert result.getCode() == Code.rc200.getCode();
        assert "审批完成".equals(result.getMsg());
    }
}
