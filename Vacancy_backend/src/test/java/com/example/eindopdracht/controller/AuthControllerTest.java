package com.example.eindopdracht.controller;

import com.example.eindopdracht.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testSignUpSuccess() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername(UUID.randomUUID().toString());
        userDto.setPassword(UUID.randomUUID().toString());
        mvc.perform(MockMvcRequestBuilders
                .post("/api/auth/signUp")
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDto.getUsername()));
    }

    @Test
    public void testSignInSuccess() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("tien");
        userDto.setPassword("tien");
        mvc.perform(MockMvcRequestBuilders
                .post("/api/auth/signIn")
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDto.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }
}
