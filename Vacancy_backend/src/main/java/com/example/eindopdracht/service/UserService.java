/**\
 * --------------------------------------------------------------------------------
 * Vacancy Web Application
 * Java Spring Boot Backend project
 * Student Name: Mustafa Dogan
 * Date:05 February 2025
 * --------------------------------------------------------------------------------
 * Service class: This basically carrying out business logic and encapsulate the app's functionality.
 */
package com.example.eindopdracht.service;

import com.example.eindopdracht.dto.AuthDto;
import com.example.eindopdracht.dto.NotificationDto;
import com.example.eindopdracht.dto.UserDto;
import com.example.eindopdracht.exception.InvalidAuthException;
import com.example.eindopdracht.exception.InvalidFileException;
import com.example.eindopdracht.exception.UserExistedException;
import com.example.eindopdracht.exception.UserIsNotExistedException;
import com.example.eindopdracht.model.SystemFile;
import com.example.eindopdracht.model.User;
import com.example.eindopdracht.model.notification;
import com.example.eindopdracht.repository.NotificationRepository;
import com.example.eindopdracht.repository.SystemFileRepository;
import com.example.eindopdracht.repository.UserRepository;
import com.example.eindopdracht.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final SystemFileRepository systemFileRepository;
    private final PasswordEncoder encoder;
    private final NotificationRepository notificationRepository;

    public UserService(UserRepository userRepository, SystemFileRepository systemFileRepository, NotificationRepository notificationRepository,PasswordEncoder encoder) {
        this.notificationRepository=notificationRepository;
        this.userRepository = userRepository;

        this.systemFileRepository = systemFileRepository;
        this.encoder = encoder;
    }
    public List<UserDto> getEmployers() {
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        List<User> employers =
                userRepository.findAll();

        return employers.stream().map(this::getUserDto).collect(Collectors.toList());
    }
    private UserDto getUserDto(User obj) {
        UserDto result = new UserDto(obj.getUsername(), obj.getPassword(),obj.getRoleName(),obj.getCvUniqueId(),obj.getCvFilename(),obj.getEmail(),obj.getPhone());
        return result;
    }

    public boolean matchRole(AuthDto authDto) {
        Optional<User> user = userRepository.findById(authDto.getUsername());
        if (user.isPresent())
            return user.get().getRoleName().equals(authDto.getRoleName());
        else
            return false;
    }
    public UserDto createUser(UserDto userDto) {
        Optional<User> user = userRepository.findById(userDto.getUsername());
        if (user.isPresent()) {
            throw new UserExistedException("User is existed");
        }

        User toSaveUser = new User();
        toSaveUser.setUsername(userDto.getUsername());
        toSaveUser.setPassword(encoder.encode(userDto.getPassword()));
        toSaveUser.setRoleName(userDto.getRoleName());
        User userReturned = userRepository.save(toSaveUser);
        System.out.println("User created: " + userReturned);

        // save notification
        NotificationDto objNoti=new NotificationDto(userDto.getUsername(),"you have successfully registered with us");
        new NotificationService(notificationRepository).createNotification(objNoti);

        return userDto;
    }

    public UserDto uploadCv(MultipartFile file) throws Exception {
        User userContext = getLoggedInUser();

        Optional<User> uo = userRepository.findById(userContext.getUsername());
        User user = uo.orElseThrow(() -> new UserIsNotExistedException("User doesn't existed"));

        SystemFile systemFile = new SystemFile();
        systemFile.setFilename(file.getOriginalFilename());
        systemFile.setCreateDatetime(new Date());
        systemFile.setData(file.getBytes());
        systemFile.setFileId(UUID.randomUUID().toString());
        systemFileRepository.save(systemFile);

        user.setCvUniqueId(systemFile.getFileId());
        user.setCvFilename(systemFile.getFilename());
        userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setCvUniqueId(user.getCvUniqueId());
        userDto.setCvFilename(systemFile.getFilename());

        // save notification
        NotificationDto objNoti=new NotificationDto(userDto.getUsername(),"you have successfully uploaded cv filename:" + userDto.getCvFilename());
        new NotificationService(notificationRepository).createNotification(objNoti);


        return userDto;
    }

    public UserDto getMyInfo() {
        User userContext = getLoggedInUser();

        Optional<User> uo = userRepository.findById(userContext.getUsername());
        User user = uo.orElseThrow(() -> new UserIsNotExistedException("User doesn't existed"));

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setCvUniqueId(user.getCvUniqueId());
        userDto.setCvFilename(user.getCvFilename());
        return userDto;
    }

    @Transactional
    public SystemFile downloadCv(String fileId) {
        User userContext = getLoggedInUser();

        Optional<User> uo = userRepository.findById(userContext.getUsername());
        User user = uo.orElseThrow(() -> new UserIsNotExistedException("User doesn't existed"));

        SystemFile systemFile = systemFileRepository.findByFileId(fileId);
        if (systemFile == null) {
            throw new InvalidFileException("File doesn't existed");
        }
        return systemFile;
    }

    public User getLoggedInUser() {
        try {
            User userContext = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            return userContext;
        } catch (Exception ex) {
            throw new InvalidAuthException("Invalid user token");
        }
    }
}
