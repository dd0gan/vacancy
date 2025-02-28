/**\
 * --------------------------------------------------------------------------------
 * Vacancy Web Application
 * Java Spring Boot Backend project
 * Student Name: Mustafa Dogan
 * Date:05 February 2025
 * --------------------------------------------------------------------------------
 * Repository class : That manage data persistence and read from database.
 * Perform CRUD Operations (Read, Update, Delete and  Create)
 */
package com.example.eindopdracht.repository;

import com.example.eindopdracht.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    List<Vacancy> findByUsername(String username);
    //Iterable<Object> findByUsername(String username);
}
