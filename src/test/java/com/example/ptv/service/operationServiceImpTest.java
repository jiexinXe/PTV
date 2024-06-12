package com.example.ptv.service;

import com.example.ptv.service.Imp.operationServiceImp;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class operationServiceImpTest {

    @InjectMocks
    private operationServiceImp operationServiceImp;

    @Test
    void testFgetItemInfo() {
        String itemId = "123";

        Rest result = operationServiceImp.fgetItemInfo(itemId);

        assertNotNull(result);
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("该货物的实体对象", result.getMsg());
    }

    @Test
    void testFgetAllitemInfo() {
        String userId = "456";

        Rest result = operationServiceImp.fgetAllitemInfo(userId);

        assertNotNull(result);
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("该用户的所有货物的简要信息", result.getMsg());
    }

    @Test
    void testFgetItem() {
        String itemId = "123";

        Rest result = operationServiceImp.fgetItem(itemId);

        assertNotNull(result);
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("item类型的对象，操作结果", result.getMsg());
    }

    @Test
    void testFdeposit() {
        String itemName = "item1";
        String itemType = "type1";
        String itemInfo = "info1";

        Rest result = operationServiceImp.fdeposit(itemName, itemType, itemInfo);

        assertNotNull(result);
        assertEquals(Code.rc200.getCode(), result.getCode());
        assertEquals("操作结果", result.getMsg());
    }
}
