package com.zmkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zmkj.dao.UserDao;
import com.zmkj.entity.UserEntity;
import com.zmkj.service.UserItemService;
import com.zmkj.service.UserService;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.security.Provider;

@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
}
