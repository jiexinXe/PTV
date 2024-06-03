package com.example.ptv.service.Imp;

import com.example.ptv.entity.User;
import com.example.ptv.service.RegistrationService;

import com.example.ptv.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private userService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean registerUser(Map<String, Object> params) {
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        String role = (String) params.get("roleId");
        int roleId = Integer.parseInt(role);
        String address = (String) params.get("address");
        String email = (String) params.get("email");
        String gender = (String) params.get("gender");
        String phone = (String) params.get("phone");
        // 检查用户是否已经存在
        if (usersService.getByName(username) != null) {
            return false; // 用户已存在，注册失败
        }

        // 创建新用户
        User newUser = new User();

        newUser.setUsername(username);
        // 对密码进行加密
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encryptedPassword);
        newUser.setRole(roleId);
        newUser.setAddress(address);
        newUser.setEmail(email);
        newUser.setGender(gender);
        newUser.setPhone(phone);

        usersService.save(newUser);
        System.out.println(newUser.getUsername());

       return true;

    }
}
