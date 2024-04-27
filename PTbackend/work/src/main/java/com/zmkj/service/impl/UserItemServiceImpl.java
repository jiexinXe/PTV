package com.zmkj.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zmkj.dao.UserItemDao;
import com.zmkj.entity.UserItemEntity;
import com.zmkj.service.UserItemService;
import org.springframework.stereotype.Service;


@Service("UserItemService")
public class UserItemServiceImpl extends ServiceImpl<UserItemDao, UserItemEntity> implements UserItemService {
}
