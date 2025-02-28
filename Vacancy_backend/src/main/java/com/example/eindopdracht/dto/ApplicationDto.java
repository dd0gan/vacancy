/**\
 * --------------------------------------------------------------------------------
 * Vacancy Web Application
 * Java Spring Boot Backend project
 * Student Name: Mustafa Dogan
 * Date:05 February 2025
 * --------------------------------------------------------------------------------
 * Data Transfer Object (DTO): the class object  transfers data between various layers of an application
 */

// package
package com.example.eindopdracht.dto;

//libraries
import javax.validation.constraints.NotEmpty;

// application DTO class
public class ApplicationDto {
    private Integer id;
    @NotEmpty
    private UserDto user;
    @NotEmpty
    private VacancyDto vacancy;
    @NotEmpty
    private String status;

    public ApplicationDto() {
    }

    public ApplicationDto(Integer id, UserDto user, VacancyDto vacancy) {
        this.id = id;
        this.user = user;
        this.vacancy = vacancy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public VacancyDto getVacancy() {
        return vacancy;
    }

    public void setVacancy(VacancyDto vacancy) {
        this.vacancy = vacancy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
