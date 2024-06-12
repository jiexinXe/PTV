package com.example.ptv.service;

import com.example.ptv.service.Imp.loginServiceImp;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class loginServiceImpTest {

    @InjectMocks
    private loginServiceImp loginServiceImp;

    @Test
    void testFchecklogin() {
        String userId = "testUser";
        String passWord = "testPassword";

        Rest result = loginServiceImp.fchecklogin(userId, passWord);

        assertNotNull(result);
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("登录成功", result.getMsg());
    }

    @Test
    void testFregister() {
        String userName = "testUser";
        String passWord = "testPassword";
        String userRole = "personal";

        Rest result = loginServiceImp.fregister(userName, passWord, userRole);

        assertNotNull(result);
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("注册成功", result.getMsg());
    }
}
