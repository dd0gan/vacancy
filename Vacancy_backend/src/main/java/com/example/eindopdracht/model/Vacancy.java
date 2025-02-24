/**\
 * --------------------------------------------------------------------------------
 * Vacancy Web Application
 * Java Spring Boot Backend project
 * Student Name: Mustafa Dogan
 * Date:05 February 2025
 * --------------------------------------------------------------------------------
 * Model class : It is holder of context data passed by a Controller
 */

package com.example.eindopdracht.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table (name = "vacancies")
public class Vacancy {

    public enum VacancyStatus {
        OPEN,
        CLOSE
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer id;

    private String title;

    private String skills;
    private String organization;

    private String description;

    private double hourlyRate;

    private double workingHour;

    private String username;

    @NotEmpty
    private String type;

    private String location;

    @Enumerated (EnumType.STRING)
    @Column(nullable = false)
    private VacancyStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vacancy")
    private List<Application> applications;

    public Vacancy() {
    }

    public Vacancy(Integer id,  String title,String skills, String description, double hourlyRate, double workingHour, String type, String location, String status,String username) {
        this.id = id;
        this.title = title;
        this.skills = skills;
        this.description = description;
        this.hourlyRate = hourlyRate;
        this.workingHour = workingHour;
        this.type = type;
        this.location = location;
        this.status = VacancyStatus.valueOf(status);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSkills() {
        return skills;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public VacancyStatus getStatus() {
        return status;
    }

    public void setStatus(VacancyStatus status) {
        this.status = status;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
