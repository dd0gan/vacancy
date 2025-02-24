package com.example.eindopdracht.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthDtoTest {
    @Test
    public void testAuthDto() {
        AuthDto authDto = new AuthDto();
        assertNotNull(authDto);
    }
}
