package com.example.eindopdracht.service;

import com.example.eindopdracht.dto.MessageDto;
import com.example.eindopdracht.dto.VacancyDto;
import com.example.eindopdracht.model.Application;
import com.example.eindopdracht.model.User;
import com.example.eindopdracht.model.Vacancy;
import com.example.eindopdracht.repository.ApplicationRepository;
import com.example.eindopdracht.repository.VacancyRepository;
import com.example.eindopdracht.security.MyUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VacancyServiceTest {

    @Mock
    VacancyRepository vacancyRepository;

    @Mock
    ApplicationRepository applicationRepository;

    @InjectMocks
    VacancyService vacancyService;

    @Test
    public void testGetAllVacancySuccess() throws Exception {

        when(vacancyRepository.findAll()).thenReturn(List.of(new Vacancy(1, "test", 10, 15, "Casual", "OK", "OPEN")));

        List<VacancyDto> result = vacancyService.getAllVacancies();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("test", result.get(0).getDescription());
    }

    @Test
    public void testCreateVacancySuccess() throws Exception {
        when(vacancyRepository.save(any())).thenReturn(new Vacancy(1, "test", 10, 15, "Casual", "US", "OPEN"));

        VacancyDto result = vacancyService.createVacancy(new VacancyDto(1, "test", 10, 15, "Casual", "US", "OPEN"));

        assertEquals(1, result.getId());
        assertEquals("test", result.getDescription());
    }

    @Test
    public void testUpdateVacancySuccess() throws Exception {
        Vacancy vacancy = new Vacancy(1, "test", 10, 15, "Casual", "US", "OPEN");
        when(vacancyRepository.findById(eq(1))).thenReturn(Optional.of(vacancy));

        vacancy.setLocation("UK");
        when(vacancyRepository.save(any())).thenReturn(vacancy);
        VacancyDto result = vacancyService.updateVacancy(new VacancyDto(1, "test", 10, 15, "Casual", "UK", "OPEN"));

        assertEquals(1, result.getId());
        assertEquals("test", result.getDescription());
        assertEquals("UK", result.getLocation());
    }

    @Test
    public void testGetVacancySuccess() throws Exception {

        Vacancy vacancy = new Vacancy(1, "test", 10, 15, "Casual", "US", "OPEN");
        when(vacancyRepository.findById(eq(1))).thenReturn(Optional.of(vacancy));

        VacancyDto result = vacancyService.getVacancy(1);

        assertEquals(1, result.getId());
        assertEquals("test", result.getDescription());
    }

    @Test
    public void testApplySuccess() throws Exception {
        Vacancy vacancy = new Vacancy(1, "test", 10, 15, "Casual", "US", "OPEN");
        when(vacancyRepository.findById(eq(1))).thenReturn(Optional.of(vacancy));

        User user = new User();
        user.setUsername("testUser");

        MyUserDetails authenticatedUser = new MyUserDetails(user);
        Authentication authentication = new TestingAuthenticationToken(authenticatedUser, "testUser");
        SecurityContext securityContext = new SecurityContextImpl(authentication);
        SecurityContextHolder.setContext(securityContext);

        Application application = new Application();
        application.setVacancy(vacancy);
        application.setEmployee(user);
        vacancy.setApplications(List.of());
        when(applicationRepository.save(any())).thenReturn(application);

        MessageDto result = vacancyService.apply(1);
        assertEquals(result.getMessage(), "Applied successfully");
    }

    @Test
    public void testCompleteSuccess() throws Exception {
        Vacancy vacancy = new Vacancy(1, "test", 10, 15, "Casual", "US", "OPEN");
        when(vacancyRepository.findById(eq(1))).thenReturn(Optional.of(vacancy));

        User user = new User();
        user.setUsername("testUser");

        MyUserDetails authenticatedUser = new MyUserDetails(user);
        Authentication authentication = new TestingAuthenticationToken(authenticatedUser, "testUser");
        SecurityContext securityContext = new SecurityContextImpl(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(applicationRepository.findById(any())).thenReturn(Optional.of(new Application()));

        MessageDto result = vacancyService.complete(1, 1, "ACCEPTED");
        assertEquals(result.getMessage(), "Completed successfully");

        MessageDto result2 = vacancyService.complete(1, 1, "REJECTED");
        assertEquals(result2.getMessage(), "Completed successfully");
    }
}
