package com.example.eindopdracht.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthResponseDtoTest {

    @Test
    public void testAuthResponseDto() {
        AuthResponseDto authResponseDto = new AuthResponseDto("test", "test");
        assertNotNull(authResponseDto);
    }
}
