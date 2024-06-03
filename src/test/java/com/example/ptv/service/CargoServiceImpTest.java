package com.example.ptv.service;

import com.example.ptv.dao.CargoDao;
import com.example.ptv.dao.ShelvesDao;
import com.example.ptv.dao.UserDao;
import com.example.ptv.entity.Cargo;
import com.example.ptv.entity.ShelvesEntity;
import com.example.ptv.entity.User;
import com.example.ptv.service.CarService;
import com.example.ptv.service.Imp.CargoServiceImp;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CargoServiceImpTest {

    @InjectMocks
    private CargoServiceImp cargoService;

    @Mock
    private CargoDao cargoDao;

    @Mock
    private ShelvesDao shelvesDao;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private UserDao userDao;

    @Mock
    private CarService carService;

    private Cargo cargo;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cargo = new Cargo();
        cargo.setCid(1);
        cargo.setNum(10);
        cargo.setStatus(0);

        user = new User();
        user.setId(1);
        user.setRole(3);
    }

    @Test
    void testAddCargo() {
        when(userDao.selectOne(any())).thenReturn(user);
        when(cargoDao.insert(any())).thenReturn(1);

        boolean result = cargoService.addCargo(cargo, "1");
        assertEquals(true, result);

        verify(kafkaTemplate, times(1)).send(eq("cargo-info"), any(String.class));
    }

    @Test
    void testDeleteCargo() {
        when(cargoDao.selectOne(any())).thenReturn(cargo);
        ShelvesEntity shelvesEntity = new ShelvesEntity();
        shelvesEntity.setCargoId("1");
        when(shelvesDao.selectOne(any())).thenReturn(shelvesEntity);

        Rest result = cargoService.deleteCargo("1", "5");
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("成功提取", result.getMsg());
    }

    @Test
    void testUpdateCargo() {
        when(cargoDao.update(any(), any())).thenReturn(1);
        boolean result = cargoService.updateCargo(cargo);
        assertEquals(true, result);
    }

    @Test
    void testGetCargoById() {
        when(cargoDao.selectOne(any())).thenReturn(cargo);
        Cargo result = cargoService.getCargoById("1");
        assertEquals(cargo, result);
    }

    @Test
    void testGetCargoListByUserId() {
        when(userDao.selectById(any())).thenReturn(user);
        when(cargoDao.selectList(any())).thenReturn(Arrays.asList(cargo));

        Rest result = cargoService.getCargoListByUserId("1");
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("货物信息列表", result.getMsg());
    }

    @Test
    void testGetShelveIdOfCargo() {
        ShelvesEntity shelvesEntity = new ShelvesEntity();
        shelvesEntity.setShelveId("1");
        shelvesEntity.setNumColumn("2");
        shelvesEntity.setNumRow("3");
        when(shelvesDao.selectList(any())).thenReturn(Arrays.asList(shelvesEntity));

        String result = cargoService.getShelveIdOfCargo("1");
        assertEquals("S1C13;", result);
    }
}
