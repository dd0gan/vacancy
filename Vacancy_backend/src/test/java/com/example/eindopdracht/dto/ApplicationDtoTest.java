package com.example.eindopdracht.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApplicationDtoTest {

    @Test
    public void testApplicationDto() {
        ApplicationDto applicationDto = new ApplicationDto(1, new UserDto(), new VacancyDto());
        assertNotNull(applicationDto);
    }
}
