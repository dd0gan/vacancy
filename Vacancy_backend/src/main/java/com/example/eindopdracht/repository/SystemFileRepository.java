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

import com.example.eindopdracht.model.SystemFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemFileRepository extends JpaRepository<SystemFile, Integer> {
    public SystemFile findByFileId(String fileId);
}
