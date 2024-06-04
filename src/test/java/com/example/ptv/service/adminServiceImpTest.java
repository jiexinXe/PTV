//package com.example.ptv.service;
//
//import com.example.ptv.service.Imp.adminServiceImp;
//import com.example.ptv.utils.Code;
//import com.example.ptv.utils.Rest;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class adminServiceImpTest {
//
//    private final adminServiceImp adminService = new adminServiceImp();
//
//    @Test
//    public void testFgetListofWH() {
//        Rest response = adminService.fgetListofWH();
//        assertEquals(Code.rc200.getCode(), response.getCode());
//        assertEquals("这里是可用仓库的列表", response.getMsg());
//    }
//
//    @Test
//    public void testFgetWHinfo() {
//        Rest response = adminService.fgetWHinfo("WH123");
//        assertEquals(Code.rc200.getCode(), response.getCode());
//        assertEquals("对应的WH类型的对象", response.getMsg());
//    }
//
//    @Test
//    public void testFchangeWH() {
//        Rest response = adminService.fchangeWH("WH123", "item", "variable");
//        assertEquals(Code.rc200.getCode(), response.getCode());
//        assertEquals("操作结果", response.getMsg());
//    }
//
//    @Test
//    public void testFdeleteWH() {
//        Rest response = adminService.fdeleteWH("WH123");
//        assertEquals(Code.rc200.getCode(), response.getCode());
//        assertEquals("操作结果", response.getMsg());
//    }
//
//    @Test
//    public void testFaddWH() {
//        Rest response = adminService.faddWH("type", "maxContext", "nowContext");
//        assertEquals(Code.rc200.getCode(), response.getCode());
//        assertEquals("操作结果", response.getMsg());
//    }
//}
