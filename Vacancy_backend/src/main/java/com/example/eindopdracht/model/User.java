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
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String roleName;




    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Application> applications;

    // uuid for latest cv file
    private String cvUniqueId;
    private String cvFilename;
    private String email;
    private String phone;

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public String getRoleName() {return roleName;}
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
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
