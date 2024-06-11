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


    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private final Object lock3 = new Object();
    private final Object lock4 = new Object();


    //获取队列中的orderId，小车处理过程用sleep代替
    //选择当前为空闲状态的小车，status置为1，代表忙碌中，current_task设置为orderId
    //处理完后，向消息队列发送orderId
    @Override
    public void processOrder(Integer orderId) {
        new Thread(() -> {
            String cargo_id = ordersdao.getCargoId(orderId.toString());
            Cargo cargo = cargodao.selectById(cargo_id);


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
                    Thread.sleep(3000); // 等待3秒
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 恢复中断状态
                    break; // 在异常情况下退出循环
                }
            }

            if (cars != null && !cars.isEmpty()) {
                Car firstCar = cars.get(0); // 获取列表中的第一个Car对象
                carDao.updateCarStatusAndTask(firstCar.getId(), 1, "当前正在处理订单" + orderId);
                System.out.println("车车" + firstCar.getId() + "来咯！ 正在处理订单: " + orderId);
                try {
                    Thread.sleep(3000); // 等待3秒
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 恢复中断状态
                }

                //货架运输中
                cargo.setStatus(3);
                // 订单处理
                int storeAttempts = 0;
                while (!storeCargoForOrder(orderId)) {
                    storeAttempts++;
                    if (storeAttempts >= 10) {
                        System.out.println("存储失败：无法在任何货架上存储订单 " + orderId + " 的货物。");
                        break;
                    }
                    try {
                        Thread.sleep(3000); // 等待3秒后重试
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // 恢复中断状态
                        break; // 在异常情况下退出循环
                    }
                }
                carDao.updateCarStatusAndTask(firstCar.getId(), 0, "无");
                System.out.println("车车" + firstCar.getId() + "处理完毕！");
                kafkaTemplate.send("car-processing", gson.toJson(orderId));
            } else {
                System.out.println("没有可用的车来处理订单: " + orderId);
            }
        }).start();
    }

    @Override
    public Rest searchLocationInShelves(String order_id) {
        return null;
    }

    private boolean storeCargoForOrder(Integer orderId) {
        String cargo_id = ordersdao.getCargoId(orderId.toString());
        System.out.println("订单对应货物id" + cargo_id);
        Cargo cargo = cargodao.selectById(cargo_id);

        // 获取货物数量
        int num = cargo.getNum();
        // 获取货物所属仓库ID
        String warehouse_id = cargo.getWarehouseId();

        boolean allShelvesFull = true;

        // 遍历货架ID（货架ID为1, 2, 3, 4）
        for (int shelveId = 1; shelveId <= 4; shelveId++) {
            boolean success = false;
            switch (shelveId) {
                case 1:
                    synchronized (lock1) {
                        success = storeCargoInShelf(warehouse_id, shelveId, cargo_id, num);
                    }
                    break;
                case 2:
                    synchronized (lock2) {
                        success = storeCargoInShelf(warehouse_id, shelveId, cargo_id, num);
                    }
                    break;
                case 3:
                    synchronized (lock3) {
                        success = storeCargoInShelf(warehouse_id, shelveId, cargo_id, num);
                    }
                    break;
                case 4:
                    synchronized (lock4) {
                        success = storeCargoInShelf(warehouse_id, shelveId, cargo_id, num);
                    }
                    break;
            }
            if (success) {
                //运输完成
                cargo.setStatus(4);
                cargodao.updateById(cargo);
                allShelvesFull = false;
                break; // 成功存储后退出循环
            }
        }

        return !allShelvesFull;
    }

    private boolean storeCargoInShelf(String warehouse_id, int shelveId, String cargo_id, int num) {
        QueryWrapper<ShelvesEntity> shelveswrapper = new QueryWrapper<>();
        shelveswrapper.eq("warehouse_id", warehouse_id)
                .eq("shelve_id", String.valueOf(shelveId))
                .isNull("cargo_id");
        List<ShelvesEntity> shelvesList = shelvesdao.selectList(shelveswrapper);
        System.out.println("检查货架 " + shelveId);
        // 遍历当前货架
        for (ShelvesEntity shelf : shelvesList) {
            shelf.setCargoId(cargo_id);
            shelvesdao.updateById(shelf);
            num--;
            // 如果货物数量为0，则返回
            if (num == 0) {
                System.out.println("货物已全部存储");
                return true;
            }
        }
        return false;
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
                Thread.sleep(8000); // 等待8秒
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
                Thread.sleep(8000); // 等待8秒
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
