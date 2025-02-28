package com.example.eindopdracht.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckHTTPResponse {

    @LocalServerPort
    private int port;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldCheckHTTPResponse() {

        assertEquals ("Hellow World",testRestTemplate.getForObject("http://localhost:"+port +"/",
                String.class));

    }




}
