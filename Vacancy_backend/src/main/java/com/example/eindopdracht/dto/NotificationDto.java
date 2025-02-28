/**\
 * --------------------------------------------------------------------------------
 * Vacancy Web Application
 * Java Spring Boot Backend project
 * Student Name: Mustafa Dogan
 * Date:05 February 2025
 * --------------------------------------------------------------------------------
 * Data Transfer Object (DTO): the class object  transfers data between various layers of an application
 */
package com.example.eindopdracht.dto;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationDto {

    private Integer ID;

    private String username;

    private String description;

    private Date dateTime;


    List<ApplicationDto> applications = new ArrayList<>();

    public NotificationDto() {
    }

    public NotificationDto(String username, String description) {
        this.username = username;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ApplicationDto> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationDto> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "NotificationDto{" +
                "username=" + username +
                ", description='" + description + '\'' +
                ", applications=" + applications +
                '}';
    }
}
