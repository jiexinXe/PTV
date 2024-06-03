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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class userServiceImpTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private userServiceImp userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserinfo() {
        // Arrange
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        user.setRole(1);
        user.setUsername("johndoe");
        user.setPassword("password");
        user.setGender("Male");
        user.setAddress("123 Main St");
        user.setEmail("johndoe@example.com");
        user.setPhone("123-456-7890");
        user.setLastLogin(new Date());

        when(userDao.selectById(userId)).thenReturn(user);

        // Act
        Rest result = userService.getUserinfo(userId);

        // Assert
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("获取成功", result.getMsg());
        verify(userDao, times(1)).selectById(userId);
        verify(userDao, times(1)).updateById(any(User.class));
    }

    @Test
    public void testChangeUserinfo() {
        // Arrange
        String userId = "1";
        String changeItem = "profile";
        Map<String, Object> changeVariable = new HashMap<>();
        changeVariable.put("name", "New Name");
        changeVariable.put("username", "newusername");

        User user = new User();
        user.setId(Integer.parseInt(userId));
        user.setName("Old Name");
        user.setUsername("oldusername");

        when(userDao.selectById(userId)).thenReturn(user);

        // Act
        Rest result = userService.changeUserinfo(userId, changeItem, changeVariable);

        // Assert
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("修改成功", result.getMsg());
        assertEquals("New Name", user.getName());
        assertEquals("newusername", user.getUsername());
        verify(userDao, times(1)).selectById(userId);
        verify(userDao, times(1)).updateById(any(User.class));
    }

    @Test
    public void testGetByName() {
        // Arrange
        String username = "johndoe";
        User user = new User();
        user.setUsername(username);

        when(userDao.selectOne(any(QueryWrapper.class))).thenReturn(user);

        // Act
        User result = userService.getByName(username);

        // Assert
        assertEquals(user, result);
        verify(userDao, times(1)).selectOne(any(QueryWrapper.class));
    }
}
