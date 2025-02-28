/**\
 * --------------------------------------------------------------------------------
 * Vacancy Web Application
 * Java Spring Boot Backend project
 * Student Name: Mustafa Dogan
 * Date:05 February 2025
 * --------------------------------------------------------------------------------
 * Controller class
 */

// package
package com.example.eindopdracht.controller;

// libraries
import com.example.eindopdracht.dto.ErrorDto;
import com.example.eindopdracht.exception.InvalidApplicationException;
import com.example.eindopdracht.exception.InvalidAuthException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;

// class
@ControllerAdvice
public class GlobalAdviceController {

    @ExceptionHandler({ InvalidApplicationException.class })
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return new ResponseEntity<>(new ErrorDto(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), ExceptionUtils.getStackTrace(ex)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ InvalidAuthException.class })
    public ResponseEntity<ErrorDto> handleAuthException(Exception ex) {
        return new ResponseEntity<>(new ErrorDto(new Date(), HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex.getMessage(), ExceptionUtils.getStackTrace(ex)), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<ErrorDto> handleValidationException(Exception ex) {
        return new ResponseEntity<>(new ErrorDto(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), ExceptionUtils.getStackTrace(ex)), HttpStatus.BAD_REQUEST);
    }
}
