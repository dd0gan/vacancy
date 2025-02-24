package com.example.eindopdracht.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ErrorDtoTest {

    @Test
    public void testErrorDto() {
        ErrorDto errorDto = new ErrorDto(new Date(), 1, "error", "test", "/123");
        assertNotNull(errorDto);
    }
}
