/**\
 * --------------------------------------------------------------------------------
 * Vacancy Web Application
 * Java Spring Boot Backend project
 * Student Name: Mustafa Dogan
 * Date:05 February 2025
 * --------------------------------------------------------------------------------
 * Data Transfer Object (DTO)
 */

//package
package com.example.eindopdracht.dto;

//libraies
import javax.validation.constraints.NotEmpty;


// authentication DTO class
public class AuthDto {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String roleName;

    public AuthDto(String username, String password, String roleName) {
        this.username = username;
        this.password = password;
        this.roleName = roleName;
    }

    public AuthDto() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

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
}
