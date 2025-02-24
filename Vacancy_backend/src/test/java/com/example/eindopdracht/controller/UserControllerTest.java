package com.example.eindopdracht.controller;

import com.example.eindopdracht.dto.AuthResponseDto;
import com.example.eindopdracht.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testGetMyInfoSuccess() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("tien");
        userDto.setPassword("tien");
        String response = mvc.perform(MockMvcRequestBuilders
                .post("/api/auth/signIn")
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDto.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readValue(response, AuthResponseDto.class).getToken();

        mvc.perform(MockMvcRequestBuilders
                .get("/api/users/me")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDto.getUsername()));
    }

    @Test
    public void testUploadDownloadCVSuccess() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("tien");
        userDto.setPassword("tien");
        String response = mvc.perform(MockMvcRequestBuilders
                .post("/api/auth/signIn")
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDto.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readValue(response, AuthResponseDto.class).getToken();

        MockMultipartFile testFile = new MockMultipartFile("testcv.txt", "", "", "{\"key1\": \"value1\"}".getBytes());

        String response2 = mvc.perform(MockMvcRequestBuilders
                .multipart("/api/users/cv/upload")
                .file("file", testFile.getBytes())
                .characterEncoding("UTF-8")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String fileId = objectMapper.readValue(response2, UserDto.class).getCvUniqueId();

        String response3 = mvc.perform(MockMvcRequestBuilders
                .get("/api/users/cv/download")
                .param("fileId", fileId)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
