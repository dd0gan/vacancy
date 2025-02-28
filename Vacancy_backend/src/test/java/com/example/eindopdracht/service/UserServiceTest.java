package com.example.eindopdracht.service;

import com.example.eindopdracht.dto.UserDto;
import com.example.eindopdracht.model.Role;
import com.example.eindopdracht.model.SystemFile;
import com.example.eindopdracht.model.User;
import com.example.eindopdracht.repository.RoleRepository;
import com.example.eindopdracht.repository.SystemFileRepository;
import com.example.eindopdracht.repository.UserRepository;
import com.example.eindopdracht.security.MyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    SystemFileRepository systemFileRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void startUp() {

    }

    @Test
    public void testCreateUserSuccess() throws Exception {
        when(userRepository.findById(eq("testUser"))).thenReturn(Optional.empty());
        when(roleRepository.findById(eq("USER"))).thenReturn(Optional.of(new Role("USER")));
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");

        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        UserDto result = userService.createUser(userDto);
        assertNotNull(result);
        assertEquals(result.getUsername(), userDto.getUsername());
    }

    @Test
    public void testUploadCvSuccess() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findById(eq("testUser"))).thenReturn(Optional.of(user));

        MyUserDetails authenticatedUser = new MyUserDetails(user);
        Authentication authentication = new TestingAuthenticationToken(authenticatedUser, "testUser");
        SecurityContext securityContext = new SecurityContextImpl(authentication);
        SecurityContextHolder.setContext(securityContext);

        MultipartFile file = new MockMultipartFile("test.txt", new byte[]{});

        UserDto result = userService.uploadCv(file);
        assertNotNull(result);
        assertEquals(result.getUsername(), user.getUsername());
        assertNotNull(result.getCvUniqueId());
    }

    @Test
    public void testDownloadCvSuccess() throws Exception {
        this.testUploadCvSuccess();
        when(systemFileRepository.findByFileId(anyString())).thenReturn(new SystemFile());

        SystemFile result = userService.downloadCv(UUID.randomUUID().toString());
        assertNotNull(result);
    }
}
