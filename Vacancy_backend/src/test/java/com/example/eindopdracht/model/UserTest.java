package com.example.eindopdracht.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {
    @Test
    public void testUser() {
        User user = new User();
        assertNotNull(user);
    }
}
