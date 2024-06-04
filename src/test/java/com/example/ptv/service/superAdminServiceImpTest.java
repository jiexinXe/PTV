package com.example.ptv.service;

import com.example.ptv.service.Imp.superAdminServiceImp;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class superAdminServiceImpTest {

    @InjectMocks
    private superAdminServiceImp superAdminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFdeleteUser() {
        Rest result = superAdminService.fdeleteUser("123");
        assertEquals(new Rest(Code.rc200.getCode(), "删除成功"), result);
    }

    @Test
    void testFaddUser() {
        Rest result = superAdminService.faddUser("userName", "userRole", "userInfo");
        assertEquals(new Rest(Code.rc200.getCode(), "操作结果"), result);
    }

    @Test
    void testFchangeUserinfo() {
        Rest result = superAdminService.fchangeUserinfo("userId", "changeItem", "changeVariable");
        assertEquals(new Rest(Code.rc200.getCode(), "修改结果"), result);
    }

    @Test
    void testFgetUserinfo() {
        Rest result = superAdminService.fgetUserinfo("123");
        assertEquals(new Rest(Code.rc200.getCode(), "这里是user类型对象"), result);
    }

    @Test
    void testFgetallUserinfo() {
        Rest result = superAdminService.fgetallUserinfo();
        assertEquals(new Rest(Code.rc200.getCode(), "这里是用户信息的对象列表"), result);
    }
}
