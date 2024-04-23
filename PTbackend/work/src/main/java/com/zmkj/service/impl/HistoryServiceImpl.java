package com.zmkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zmkj.dao.HistoryDao;
import com.zmkj.entity.HistoryEntity;
import com.zmkj.service.HistoryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("HistoryService")
public class HistoryServiceImpl extends ServiceImpl<HistoryDao, HistoryEntity> implements HistoryService {

}
