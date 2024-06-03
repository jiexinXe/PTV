package com.example.ptv.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ptv.dao.CargoDao;
import com.example.ptv.dao.ShelvesDao;
import com.example.ptv.dao.warehouseDao;
import com.example.ptv.entity.Cargo;
import com.example.ptv.entity.ShelvesEntity;
import com.example.ptv.service.Imp.ShelvesServiceImpl;
import com.example.ptv.service.ShelvesService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.PageUtils;
import com.example.ptv.utils.Query;
import com.example.ptv.utils.Rest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShelvesServiceImplTest {

    @Mock
    private ShelvesDao shelvesDao;

    @Mock
    private CargoDao cargoDao;

    @Mock
    private warehouseDao warehouseDao;

    @InjectMocks
    private ShelvesServiceImpl shelvesServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() {
        Rest result = shelvesServiceImpl.add();

        verify(shelvesDao, times(400)).insert(any(ShelvesEntity.class));
        assert result.getCode().equals(Code.rc200.getCode());
        assert "货架添加成功".equals(result.getMsg());
    }

    @Test
    void testGetInfo() {
        String warehouseId = "1";
        String shelveId = "1";
        List<ShelvesEntity> shelvesList = new ArrayList<>();
        shelvesList.add(new ShelvesEntity());
        when(shelvesDao.selectList(any(QueryWrapper.class))).thenReturn(shelvesList);

        Rest result = shelvesServiceImpl.getInfo(warehouseId, shelveId);

        verify(shelvesDao, times(1)).selectList(any(QueryWrapper.class));
        assert result.getCode().equals(Code.rc200.getCode());
        assert result.getData().equals(shelvesList);
        assert "货架信息".equals(result.getMsg());
    }

    @Test
    void testGetCarogoOfShelve() {
        String warehouseId = "1";
        String shelveId = "1";
        String row = "1";
        String column = "1";
        ShelvesEntity shelvesEntity = new ShelvesEntity();
        shelvesEntity.setCargoId("1");
        when(shelvesDao.selectOne(any(QueryWrapper.class))).thenReturn(shelvesEntity);

        Cargo cargo = new Cargo();
        cargo.setCid(1);
        when(cargoDao.selectById("1")).thenReturn(cargo);

        Rest result = shelvesServiceImpl.getCarogoOfShelve(warehouseId, shelveId, row, column);

        verify(shelvesDao, times(1)).selectOne(any(QueryWrapper.class));
        verify(cargoDao, times(1)).selectById("1");
        assert result.getCode().equals(Code.rc200.getCode());
        assert result.getData().equals(cargo);
        assert "该货架的货物信息".equals(result.getMsg());
    }

    @Test
    void testGetCarogoOfShelveNoCargo() {
        String warehouseId = "1";
        String shelveId = "1";
        String row = "1";
        String column = "1";
        ShelvesEntity shelvesEntity = new ShelvesEntity();
        shelvesEntity.setCargoId(null); // 确保返回null
        when(shelvesDao.selectOne(any(QueryWrapper.class))).thenReturn(shelvesEntity);

        when(cargoDao.selectById(null)).thenReturn(null); // 模拟返回null

        Rest result = shelvesServiceImpl.getCarogoOfShelve(warehouseId, shelveId, row, column);

        verify(shelvesDao, times(1)).selectOne(any(QueryWrapper.class));
        verify(cargoDao, times(1)).selectById(null);
        assert result.getCode().equals(Code.rc400.getCode());
        assert "该位置没有货物或货物信息异常".equals(result.getMsg());
    }
}
