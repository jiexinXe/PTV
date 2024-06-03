package com.example.ptv.service;

import com.example.ptv.bean.ComUser;
import com.example.ptv.entity.User;
import com.example.ptv.service.Imp.CustomUserDetailsService;
import com.example.ptv.service.userService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private userService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRole(1); // Set role to ADMIN
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        when(userService.getByName("testUser")).thenReturn(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_ADMIN", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userService.getByName("testUser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("testUser");
        });
    }

    @Test
    void testLoadUserByUsername_RoleMapping() {
        user.setRole(2); // Set role to SUPERADMIN
        when(userService.getByName("testUser")).thenReturn(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("ROLE_SUPERADMIN", userDetails.getAuthorities().iterator().next().getAuthority());

        user.setRole(3); // Set role to CUSTOMER
        when(userService.getByName("testUser")).thenReturn(user);

        userDetails = customUserDetailsService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("ROLE_CUSTOMER", userDetails.getAuthorities().iterator().next().getAuthority());
    }
}
