package com.zmkj..service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjx.common.utils.PageUtils;
import com.zjx.common.utils.Query;

import com.zmkj..dao.WarehouseDao;
import com.zmkj..entity.WarehouseEntity;
import com.zmkj..service.WarehouseService;


@Service("warehouseService")
public class WarehouseServiceImpl extends ServiceImpl<WarehouseDao, WarehouseEntity> implements WarehouseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WarehouseEntity> page = this.page(
                new Query<WarehouseEntity>().getPage(params),
                new QueryWrapper<WarehouseEntity>()
        );

        return new PageUtils(page);
    }

}