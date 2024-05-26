package com.example.ptv.service.Imp;

import com.example.ptv.dao.ShelvesDao;
import com.example.ptv.entity.ShelvesEntity;
import com.example.ptv.service.ShelvesService;
import com.example.ptv.utils.PageUtils;
import com.example.ptv.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("shelvesService")
public class ShelvesServiceImpl extends ServiceImpl<ShelvesDao, ShelvesEntity> implements ShelvesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShelvesEntity> page = this.page(
                new Query<ShelvesEntity>().getPage(params),
                new QueryWrapper<ShelvesEntity>()
        );

        return new PageUtils(page);
    }

}