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
import javax.validation.constraints.Size;

public class UserDto {
    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String username;
    @NotEmpty
    @Size(min = 2, message = "password should have at least 2 characters")
    private String password;
    private String roleName;

    // uuid for latest cv file
    private String cvUniqueId;
    private String cvFilename;
    private String email;
    private String phone;

    public UserDto(){}
    public UserDto(String username, String password, String roleName, String cvUniqueId, String cvFilename,String email, String phone) {
        this.username = username;
        this.password = password;
        this.roleName = roleName;
        this.cvUniqueId = cvUniqueId;
        this.cvFilename = cvFilename;
        this.email = email;
        this.phone = phone;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPhone() {return phone;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roles) {
        this.roleName = roles;
    }

    public String getCvUniqueId() {
        return cvUniqueId;
    }

    public void setCvUniqueId(String cvUniqueId) {
        this.cvUniqueId = cvUniqueId;
    }

    public String getCvFilename() {
        return cvFilename;
    }

    public void setCvFilename(String cvFilename) {
        this.cvFilename = cvFilename;
    }
}
