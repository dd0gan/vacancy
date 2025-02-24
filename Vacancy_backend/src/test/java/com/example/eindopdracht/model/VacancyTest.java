package com.example.eindopdracht.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VacancyTest {

    @Test
    public void testVacancy() {
        Vacancy vacancy = new Vacancy(1, "test", 10, 15, "Casual", "US", "OPEN");
        assertNotNull(vacancy);
    }
}
