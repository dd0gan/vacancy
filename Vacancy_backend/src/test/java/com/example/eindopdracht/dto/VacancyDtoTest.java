package com.example.eindopdracht.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VacancyDtoTest {
    @Test
    public void testVacancyDto() {
        VacancyDto vacancyDto = new VacancyDto(1, "test", 10.0, 8.0, "test", "test", "test");
        assertNotNull(vacancyDto);
    }
}
