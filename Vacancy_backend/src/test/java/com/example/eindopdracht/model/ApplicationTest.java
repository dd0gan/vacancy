package com.example.eindopdracht.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApplicationTest {

    @Test
    public void testApplication() {
        Application application = new Application();
        assertNotNull(application);
    }
}
