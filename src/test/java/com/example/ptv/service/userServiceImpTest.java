package com.example.ptv.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.ptv.dao.UserDao;
import com.example.ptv.entity.User;
import com.example.ptv.service.Imp.userServiceImp;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class userServiceImpTest {

    @InjectMocks
    private userServiceImp userService;

    @Mock
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserinfo() {
        User user = new User();
        user.setId(123);
        user.setName("TestUser");
        when(userDao.selectById(123)).thenReturn(user);

        Rest result = userService.getUserinfo(123);
        assertEquals(new Rest(Code.rc200.getCode(), "TestUser", "获取成功"), result);
    }

    @Test
    void testChangeUserinfo() {
        User user = new User();
        user.setId(123);
        when(userDao.selectById("123")).thenReturn(user);

        Map<String, Object> profile = new HashMap<>();
        profile.put("name", "NewName");

        Rest result = userService.changeUserinfo("123", "profile", profile);
        assertEquals(new Rest(Code.rc200.getCode(), "修改成功"), result);
    }

    @Test
    void testGetByName() {
        User user = new User();
        user.setUsername("testuser");
        when(userDao.selectOne(new QueryWrapper<User>().eq("username", "testuser"))).thenReturn(user);

        User result = userService.getByName("testuser");
        assertEquals(user, result);
    }
}
