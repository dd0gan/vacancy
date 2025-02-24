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

import com.example.eindopdracht.dto.ApplicationDto;
import com.example.eindopdracht.dto.MessageDto;
import com.example.eindopdracht.dto.UserDto;
import com.example.eindopdracht.dto.NotificationDto;
import com.example.eindopdracht.exception.InvalidApplicationException;
import com.example.eindopdracht.exception.InvalidNotificationException;
import com.example.eindopdracht.model.Application;
import com.example.eindopdracht.model.User;
import com.example.eindopdracht.model.notification;
import com.example.eindopdracht.repository.ApplicationRepository;
import com.example.eindopdracht.repository.NotificationRepository;
import com.example.eindopdracht.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository NotificationRepository;

    public NotificationService(NotificationRepository NotificationRepository) {
        this.NotificationRepository = NotificationRepository;
    }

    public List<NotificationDto> getAllNotification() {
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        List<notification> notifications =
        NotificationRepository.findByUsername(user.getUsername());

        return notifications.stream().map(this::getNotificationDto).collect(Collectors.toList());
    }

    public NotificationDto createNotification(NotificationDto v) {
        notification notification = new notification(v.getUsername(), v.getDescription());
        notification = NotificationRepository.save(notification);
        NotificationDto result = getNotificationDto(notification);
        return result;
    }

    private NotificationDto getNotificationDto(notification objNoti) {
        NotificationDto result = new NotificationDto(objNoti.getUsername(), objNoti.getDescription());

        return result;

    }

    public NotificationDto getNotification(Integer id) {
        if (id == null) {
            throw new InvalidNotificationException("Invalid Notification id is supplied");
        }
        Optional<notification> vo = NotificationRepository.findById(id);
        notification notification = vo.orElseThrow(() -> new InvalidNotificationException("Notification not found"));
        return getNotificationDto(notification);
    }

}
