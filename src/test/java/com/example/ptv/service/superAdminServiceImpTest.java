package com.example.ptv.service;

import com.example.ptv.service.Imp.superAdminServiceImp;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class superAdminServiceImpTest {

    private superAdminServiceImp superAdminService;

    @BeforeEach
    public void setUp() {
        superAdminService = new superAdminServiceImp();
    }

    @Test
    public void testFdeleteUser() {
        // Arrange
        String userId = "123";

        // Act
        Rest result = superAdminService.fdeleteUser(userId);

        // Assert
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("删除成功", result.getMsg());
    }

    @Test
    public void testFaddUser() {
        // Arrange
        String userName = "John Doe";
        String userRole = "个人";
        String userInfo = "Some info";

        // Act
        Rest result = superAdminService.faddUser(userName, userRole, userInfo);

        // Assert
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("操作结果", result.getMsg());
    }

    @Test
    public void testFchangeUserinfo() {
        // Arrange
        String userId = "123";
        String changeItem = "name";
        String changeVariable = "newName";

        // Act
        Rest result = superAdminService.fchangeUserinfo(userId, changeItem, changeVariable);

        // Assert
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("修改结果", result.getMsg());
    }

    @Test
    public void testFgetUserinfo() {
        // Arrange
        String userId = "123";

        // Act
        Rest result = superAdminService.fgetUserinfo(userId);

        // Assert
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("这里是user类型对象", result.getMsg());
    }

    @Test
    public void testFgetallUserinfo() {
        // Act
        Rest result = superAdminService.fgetallUserinfo();

        // Assert
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("这里是用户信息的对象列表", result.getMsg());
    }
}
