/**\
 * --------------------------------------------------------------------------------
 * Vacancy Web Application
 * Java Spring Boot Backend project
 * Student Name: Mustafa Dogan
 * Date:05 February 2025
 * --------------------------------------------------------------------------------
 * Class to handle Exceptions
 */
package com.example.eindopdracht.exception;

public class InvalidAuthException extends RuntimeException {

    public InvalidAuthException(String message) {
        super(message);
    }
}
