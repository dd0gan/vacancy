/**\
 * --------------------------------------------------------------------------------
 * Vacancy Web Application
 * Java Spring Boot Backend project
 * Student Name: Mustafa Dogan
 * Date:05 February 2025
 * --------------------------------------------------------------------------------
 * Controller class
 */

// package
package com.example.eindopdracht.controller;

//libraries
import com.example.eindopdracht.dto.NotificationDto;
import com.example.eindopdracht.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//notification controller class
@RestController
@RequestMapping("/api/notificaion")
public class NotificationController {

    private final NotificationService NotificationService;

    public NotificationController(NotificationService NotificationService) {
        this.NotificationService = NotificationService;
    }

    @GetMapping("")
    public ResponseEntity getAllNotification() {
        List<NotificationDto> notifications = NotificationService.getAllNotification();
        return ResponseEntity.ok().body(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity getNotification(@PathVariable Integer id) {
        NotificationDto Notification = NotificationService.getNotification(id);
        return ResponseEntity.ok().body(Notification);
    }

    @PostMapping("")
    public ResponseEntity createNotification(@RequestBody NotificationDto NotificationDto) {
        NotificationDto savedNotification = NotificationService.createNotification(NotificationDto);
        return ResponseEntity.ok().body(savedNotification);
    }
}
