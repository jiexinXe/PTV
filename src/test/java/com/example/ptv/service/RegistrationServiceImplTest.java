package com.example.ptv.service;

import com.example.ptv.entity.User;
import com.example.ptv.service.Imp.RegistrationServiceImpl;
import com.example.ptv.service.userService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {

    @Mock
    private userService usersService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    private Map<String, Object> params;

    @BeforeEach
    void setUp() {
        params = new HashMap<>();
        params.put("username", "testUser");
        params.put("password", "testPass");
        params.put("roleId", "1");
        params.put("address", "testAddress");
        params.put("email", "testEmail");
        params.put("gender", "testGender");
        params.put("phone", "testPhone");
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        when(usersService.getByName("testUser")).thenReturn(new User());

        boolean result = registrationService.registerUser(params);

        assertFalse(result);
        verify(usersService, never()).save(any(User.class));
    }

    @Test
    void testRegisterUser_Success() {
        when(usersService.getByName("testUser")).thenReturn(null);
        when(passwordEncoder.encode("testPass")).thenReturn("encryptedPass");

        boolean result = registrationService.registerUser(params);

        assertTrue(result);
        verify(usersService, times(1)).save(any(User.class));
    }
}
