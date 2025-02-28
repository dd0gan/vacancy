package com.example.eindopdracht.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageDtoTest {

    @Test
    public void testMessageDto() {
        MessageDto messageDto = new MessageDto("test");
        assertNotNull(messageDto);
    }
}
