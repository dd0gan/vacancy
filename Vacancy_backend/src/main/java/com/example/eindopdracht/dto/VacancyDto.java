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
import java.util.List;

public class VacancyDto {
    private Integer id;

    private String title;

    private String skills;

    private String organization;
    private String username;

    private String description;

    private double hourlyRate;

    private double workingHour;

    @NotEmpty
    private String type;

    private String location;

    @NotEmpty
    private String status;

    List<ApplicationDto> applications = new ArrayList<>();

    public VacancyDto() {
    }

    public VacancyDto(Integer id, String title,String username,String skills, String organization, String description, double hourlyRate, double workingHour, String type, String location, String status) {
        this.id = id;
        this.title = title;
        this.username=username;
        this.skills=skills;
        this.organization=organization;
        this.description = description;
        this.hourlyRate = hourlyRate;
        this.workingHour = workingHour;
        this.type = type;
        this.location = location;
        this.status = status;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSkills() {
        return skills;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getWorkingHour() {
        return workingHour;
    }

    public void setWorkingHour(double workingHour) {
        this.workingHour = workingHour;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ApplicationDto> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationDto> applications) {
        this.applications = applications;
    }
}
