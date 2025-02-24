package com.example.eindopdracht.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDtoTest {
    @Test
    public void testUserDto() {
        UserDto userDto = new UserDto();
        assertNotNull(userDto);
    }
}
