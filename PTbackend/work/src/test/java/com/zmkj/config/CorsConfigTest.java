package com.zmkj.config;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

public class CorsConfigTest {

    private CorsConfig corsConfigUnderTest;

    @Before
    public void setUp() {
        corsConfigUnderTest = new CorsConfig();
    }

    @Test
    public void testAddCorsMappings() {
        // Setup
        final CorsRegistry registry = new CorsRegistry();

        // Run the test
        corsConfigUnderTest.addCorsMappings(registry);

        // Verify the results
    }
}
