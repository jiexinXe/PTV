package com.example.ptv.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.ptv.dao.CarDao;
import com.example.ptv.dao.CargoDao;
import com.example.ptv.dao.ShelvesDao;
import com.example.ptv.dao.ordersDao;
import com.example.ptv.entity.*;
import com.example.ptv.service.CarService;
import com.example.ptv.utils.Rest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class CarServiceImp implements CarService {

    @Autowired
    private CarDao carDao;
    @Autowired
    ShelvesDao shelvesdao;
    @Autowired
    ordersDao ordersdao;
    @Autowired
    CargoDao cargodao;

    @Autowired
    private KafkaTemplate<String ,Object> kafkaTemplate;

    @Autowired
    private Gson gson = new GsonBuilder().create();
    @Override
    public List<Car> getAllCars() {
        return carDao.selectList(null); // 获取所有车辆信息
    }



    //获取队列中的orderId，小车处理过程用sleep代替
    //选择当前为空闲状态的小车，status置为1，代表忙碌中，current_task设置为orderId
    //处理完后，向消息队列发送orderId
    //货架位置？？？
    @Override
    public void processOrder(Integer orderId) {
        List<Car> cars = null;
        int attempts = 0;

        while (cars == null || cars.isEmpty()) {
            cars = carDao.selectCarsByStatus(0);
            if (cars != null && !cars.isEmpty()) {
                break; // 如果cars不为空，则退出循环
            }
            attempts++;
            if (attempts >= 5) { // 如果已尝试5次，则停止重试
                break;
            }
            try {
                Thread.sleep(3000); // 等待5秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
                break; // 在异常情况下退出循环
            }
        }

        if (cars != null && !cars.isEmpty()) {
            Car firstCar = cars.get(0); // 获取列表中的第一个Car对象
            carDao.updateCarStatusAndTask(firstCar.getId(), 1, "当前正在处理订单" + orderId);
            System.out.println("车车"+firstCar.getId()+"来咯！ "+"正在处理订单: " +orderId);
            try {
                Thread.sleep(3000); // 等待5秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
            }
            //处理完成
            carDao.updateCarStatusAndTask(firstCar.getId(), 0, "无");
            System.out.println("车车"+firstCar.getId()+"处理完毕！");

            //改变货物状态为4，意为运输完成
            // 因为没有货架运输，因此直接设置现在为运输完成
            String cid = ordersdao.getCargoId(String.valueOf(orderId));
            Cargo cargo = cargodao.selectById(cid);
            cargo.setStatus(4);
            cargodao.updateById(cargo);

            //自动搜索货架位置
            searchLocationInShelves(String.valueOf(orderId));
            kafkaTemplate.send("car-processing",gson.toJson(orderId));
        } else {

        }
    }

    @Override
    public Rest searchLocationInShelves(String order_id) {
        String cargo_id = ordersdao.getCargoId(order_id);
        System.out.println("订单对应货物id"+cargo_id);
        Cargo cargo = cargodao.selectById(cargo_id);
        String warehouse_id = cargo.getWarehouseId();
        Integer numOfCargo = cargo.getNum();

        QueryWrapper<ShelvesEntity> shelveswrapper = new QueryWrapper<>();
        shelveswrapper.eq("warehouse_id", warehouse_id);

        //这里缺少一个挑选货架位置的策略
        List<ShelvesEntity> shelvesList = shelvesdao.selectList(shelveswrapper);
        Collections.reverse(shelvesList);
        ShelvesEntity newlocation = new ShelvesEntity();

        for (int i=0;i<numOfCargo;i++) {

            //遍历列表，获得第一个没有放货物的货架位置
            for (ShelvesEntity se : shelvesList)
                if (Objects.isNull(se.getCargoId()) || se.getCargoId().equals("0") || se.getCargoId().equals("-1"))
                    newlocation = se;

            System.out.println("选取的货架位置是" + newlocation.getId());
            newlocation.setCargoId(cargo_id);
            shelvesdao.updateById(newlocation);
        }

        orders order = ordersdao.selectById(order_id);
        order.setStates("已入库");
        ordersdao.updateById(order);
        return null;
    }

    @Override
    public void processCargo(String cid, String sid) {
        List<Car> cars = null;
        int attempts = 0;

        while (cars == null || cars.isEmpty()) {
            cars = carDao.selectCarsByStatus(0);
            if (cars != null && !cars.isEmpty()) {
                break; // 如果cars不为空，则退出循环
            }
            attempts++;
            if (attempts >= 5) { // 如果已尝试5次，则停止重试
                break;
            }
            try {
                Thread.sleep(3000); // 等待5秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
                break; // 在异常情况下退出循环
            }
        }

        if (cars != null && !cars.isEmpty()) {
            Car firstCar = cars.get(0); // 获取列表中的第一个Car对象
            carDao.updateCarStatusAndTask(firstCar.getId(), 1, "当前正在前往货架"+sid);
            System.out.println("车车"+firstCar.getId()+"来咯！ "+"正在前往货架: "+sid);
            try {
                Thread.sleep(3000); // 等待5秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
            }
            System.out.println("车车"+firstCar.getId()+"正在提取货物: "+cid);
            //处理完成
            carDao.updateCarStatusAndTask(firstCar.getId(), 0, "无");
            System.out.println("车车"+firstCar.getId()+"处理完毕！");
        }
    }
}
