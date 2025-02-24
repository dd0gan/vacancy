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

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;


@Entity
@Table (name = "notification")
public class notification {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer ID;

    private String username;

    @Column(name = "description")
    private String description;

    @Column(name = "createdOn")
    private Date createdOn;

    public notification() {
    }

    public notification(String username, String description, Date dateTime) {
        this.username = username;
        this.description = description;
        this.createdOn = createdOn;
    }
    public notification(String username, String description) {
        this.username = username;
        this.description = description;
        java.util.Date utilDate = new java.util.Date();
        this.createdOn = new java.sql.Date(utilDate.getTime());
    }
    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateTime() {
        return createdOn;
    }

    public void setUserId(Integer userId) {
        this.username = username;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedOn(Date dateTime) {
        this.createdOn = dateTime;
    }

    @Override
    public String toString() {
        return "notification{" +
                "username=" + username +
                ", description='" + description + '\'' +
                ", dateTime=" + createdOn +
                '}';
    }
}
