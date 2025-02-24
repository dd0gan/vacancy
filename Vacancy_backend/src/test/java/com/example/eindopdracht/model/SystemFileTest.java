package com.example.eindopdracht.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SystemFileTest {

    @Test
    public void testSystemFile() {
        SystemFile systemFile = new SystemFile();
        assertNotNull(systemFile);
    }
}
