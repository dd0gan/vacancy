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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    private String roleName;


    public Role() {
    }

    public Role(String roleName) {
        this.roleName= roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRolename(String roleName) {
        this.roleName = roleName;
    }

}