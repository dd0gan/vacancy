package com.example.eindopdracht.controller;

import com.example.eindopdracht.dto.AuthResponseDto;
import com.example.eindopdracht.dto.UserDto;
import com.example.eindopdracht.dto.VacancyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VacancyControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testGetAllVacanciesSuccess() throws Exception {
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
                .get("/api/vacancies/")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUpdateVacancySuccess() throws Exception {
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

        VacancyDto vacancyDto = new VacancyDto(1, "test", 15.0, 8.0, "Casual", "US", "OPEN");
        mvc.perform(MockMvcRequestBuilders
                .post("/api/vacancies/")
                .content(objectMapper.writeValueAsString(vacancyDto))
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        vacancyDto.setStatus("CLOSE");
        mvc.perform(MockMvcRequestBuilders
                .put("/api/vacancies/")
                .content(objectMapper.writeValueAsString(vacancyDto))
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testApplyVacancySuccess() throws Exception {
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

        VacancyDto vacancyDto2 = new VacancyDto(2, "test", 15.0, 8.0, "Casual", "US", "OPEN");
        String response2 = mvc.perform(MockMvcRequestBuilders
                .post("/api/vacancies/")
                .content(objectMapper.writeValueAsString(vacancyDto2))
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Integer vacancyId = objectMapper.readValue(response2, VacancyDto.class).getId();
        assertTrue(vacancyId > 0);

        String response3 = mvc.perform(MockMvcRequestBuilders
                .post("/api/vacancies/apply")
                .param("id", vacancyId.toString())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertNotNull(response3);
    }

    @Test
    public void testCompleteVacancySuccess() throws Exception {
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

        VacancyDto vacancyDto3 = new VacancyDto(3, "test", 15.0, 8.0, "Casual", "US", "OPEN");
        String response2 = mvc.perform(MockMvcRequestBuilders
                .post("/api/vacancies/")
                .content(objectMapper.writeValueAsString(vacancyDto3))
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Integer vacancyId = objectMapper.readValue(response2, VacancyDto.class).getId();
        assertTrue(vacancyId > 0);

        String response3 = mvc.perform(MockMvcRequestBuilders
                .post("/api/vacancies/apply")
                .param("id", vacancyId.toString())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertNotNull(response3);

        String response4 = mvc.perform(MockMvcRequestBuilders
                .get("/api/vacancies/" + vacancyId)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        VacancyDto postVacancyDto = objectMapper.readValue(response4, VacancyDto.class);

        String response5 = mvc.perform(MockMvcRequestBuilders
                .post("/api/vacancies/complete")
                .param("id", vacancyId.toString())
                .param("applicationId", postVacancyDto.getApplications().get(0).getId().toString())
                .param("acceptReject", "ACCEPTED")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertNotNull(response5);

    }
}
