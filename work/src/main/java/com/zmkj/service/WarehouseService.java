package com.zmkj..service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjx.common.utils.PageUtils;
import com.zmkj..entity.WarehouseEntity;

import java.util.Map;

/**
 * 
 *
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-03-11 23:35:28
 */
public interface WarehouseService extends IService<WarehouseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

