package com.zmkj..service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjx.common.utils.PageUtils;
import com.zjx.common.utils.Query;

import com.zmkj..dao.LocationDao;
import com.zmkj..entity.LocationEntity;
import com.zmkj..service.LocationService;


@Service("locationService")
public class LocationServiceImpl extends ServiceImpl<LocationDao, LocationEntity> implements LocationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LocationEntity> page = this.page(
                new Query<LocationEntity>().getPage(params),
                new QueryWrapper<LocationEntity>()
        );

        return new PageUtils(page);
    }

}