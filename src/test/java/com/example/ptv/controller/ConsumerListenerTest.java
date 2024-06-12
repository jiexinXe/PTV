//package com.example.ptv.controller;
//
//import com.example.ptv.dao.ordersDao;
//import com.example.ptv.entity.orders;
//import com.example.ptv.service.CarService;
//import com.example.ptv.service.CargoService;
//import com.example.ptv.service.ordersService;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//
//class ConsumerListenerTest {
//
//    @Mock
//    private ordersService ordersService;
//
//    @Mock
//    private CargoService cargoService;
//
//    @Mock
//    private ordersDao ordersDao;
//
//    @Mock
//    private CarService carService;
//
//    @InjectMocks
//    private ConsumerListener consumerListener;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testConsumeMessage() {
//        String message = "123";
//        ConsumerRecord<?, ?> record = new ConsumerRecord<>("cargo-info", 0, 0L, null, message);
//        consumerListener.consumeMessage(record);
//        verify(ordersService, times(1)).autoAddOrder(Integer.valueOf(message));
//    }
//
//    @Test
//    void testConsumeMessageOrderCreated() {
//        String message = "123";
//        orders order = new orders();
//        order.setId(123);
//        order.setStates("待处理");
//
//        when(ordersDao.selectById(String.valueOf(123))).thenReturn(order);
//
//        ConsumerRecord<?, ?> record = new ConsumerRecord<>("order-approved", 0, 0L, null, message);
//        consumerListener.consumeMessageOrderCreated(record);
//
//        verify(ordersDao, times(1)).selectById(String.valueOf(123));
//        verify(ordersDao, times(1)).updateById(order);
//        verify(carService, times(1)).processOrder(Integer.valueOf(message));
//    }
//
//    @Test
//    void testConsumeMessageShelfUpdated() {
//        String message = "Shelf updated";
//        ConsumerRecord<?, ?> record = new ConsumerRecord<>("car-processing", 0, 0L, null, message);
//        consumerListener.consumeMessageShelfUpdated(record);
//        // No interaction to verify for this method
//    }
//
//    @Test
//    void testCargoBeRemoved() {
//        String message = "cargo1;10";
//        ConsumerRecord<?, ?> record = new ConsumerRecord<>("cargo-remove", 0, 0L, null, message);
//        consumerListener.cargoBeRemoved(record);
//        verify(cargoService, times(1)).deleteCargo("cargo1", "10");
//    }
//}
