package com.example.ptv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ptv.entity.ShelvesEntity;
import com.example.ptv.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-05-26 21:36:36
 */
public interface ShelvesService extends IService<ShelvesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

