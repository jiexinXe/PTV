package com.example.ptv.service;

import com.example.ptv.dao.CarDao;
import com.example.ptv.dao.CargoDao;
import com.example.ptv.dao.ShelvesDao;
import com.example.ptv.dao.ordersDao;
import com.example.ptv.entity.Car;
import com.example.ptv.entity.Cargo;
import com.example.ptv.entity.ShelvesEntity;
import com.example.ptv.service.CarService;
import com.example.ptv.service.Imp.CarServiceImp;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceImpTest {

    @InjectMocks
    private CarServiceImp carService;

    @Mock
    private CarDao carDao;

    @Mock
    private ShelvesDao shelvesDao;

    @Mock
    private ordersDao ordersDao;

    @Mock
    private CargoDao cargoDao;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private Car car;
    private Cargo cargo;
    private ShelvesEntity shelvesEntity;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        car = new Car();
        car.setId(1);
        car.setStatus(0);

        cargo = new Cargo();
        cargo.setCid(1);
        cargo.setNum(10);
        cargo.setStatus(0);

        shelvesEntity = new ShelvesEntity();
        shelvesEntity.setShelveId("1");
        shelvesEntity.setNumColumn("2");
        shelvesEntity.setNumRow("3");
        shelvesEntity.setWarehouseId("1");

        // 使用反射设置 gson 实例
        Field gsonField = CarServiceImp.class.getDeclaredField("gson");
        gsonField.setAccessible(true);
        gsonField.set(carService, new Gson());
    }

    @Test
    void testGetAllCars() {
        when(carDao.selectList(null)).thenReturn(Arrays.asList(car));

        List<Car> result = carService.getAllCars();
        assertEquals(1, result.size());
        assertEquals(car, result.get(0));
    }

    @Test
    void testProcessOrder() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        when(ordersDao.getCargoId(any())).thenReturn("1");
        when(cargoDao.selectById(any())).thenReturn(cargo);
        when(carDao.selectCarsByStatus(0)).thenReturn(Arrays.asList(car));

        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(kafkaTemplate).send(eq("car-processing"), any(String.class));

        carService.processOrder(1);

        latch.await(); // 等待线程执行完成

        verify(carDao, times(1)).updateCarStatusAndTask(eq(1), eq(1), any());
        verify(kafkaTemplate, times(1)).send(eq("car-processing"), any(String.class));
    }

    @Test
    void testProcessCargo() throws InterruptedException {
        when(carDao.selectCarsByStatus(0)).thenReturn(Arrays.asList(car));

        carService.processCargo("1", "1");

        Thread.sleep(4000); // 等待线程执行完成

        verify(carDao, times(1)).updateCarStatusAndTask(eq(1), eq(1), any());
        verify(carDao, times(1)).updateCarStatusAndTask(eq(1), eq(0), any());
    }
}
