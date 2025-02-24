/**\
 * --------------------------------------------------------------------------------
 * Vacancy Web Application
 * Java Spring Boot Backend project
 * Student Name: Mustafa Dogan
 * Date:05 February 2025
 * --------------------------------------------------------------------------------
 * Controller class
 */

// PACKAGE
package com.example.eindopdracht.controller;

// LIBRARIES
import com.example.eindopdracht.dto.AuthDto;
import com.example.eindopdracht.dto.AuthResponseDto;

import com.example.eindopdracht.dto.UserDto;
import com.example.eindopdracht.exception.UserExistedException;
import com.example.eindopdracht.repository.SystemFileRepository;
import com.example.eindopdracht.security.JwtService;
import com.example.eindopdracht.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//CLASS
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // LOCAL VARIABLES
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SystemFileRepository systemFileRepository;

    /**
     * Initilize authentication controller object using parameterized constructor
     * @param authManager
     * @param jwtService
     * @param userService
     */
    public AuthController(AuthenticationManager authManager, JwtService jwtService, UserService userService, SystemFileRepository systemFileRepository) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.systemFileRepository = systemFileRepository;
    }

    /***
     * Method to authentication Users
     * @param authDto
     * @return
     */
    @PostMapping("/signIn")
    public ResponseEntity signIn(@RequestBody AuthDto authDto)
    {
            UsernamePasswordAuthenticationToken up =
                    new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());

            if( userService.matchRole(authDto))
                System.out.println(">> User authenticated   " + authDto.getUsername()   );
            else
                return new ResponseEntity("You are not authorized to user", HttpStatus.UNAUTHORIZED);

            System.out.println(authDto.getUsername());
            System.out.println(authDto.getRoleName());

            try
            {

                // create authentication manager and authenticate user
                Authentication auth = authManager.authenticate(up);
                UserDetails ud = (UserDetails) auth.getPrincipal();

                // generate authentication tocken
                String token = jwtService.generateToken(ud);

                // user authenticated
                return ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .body(new AuthResponseDto(authDto.getUsername(), token));
            }
            catch (AuthenticationException ex)
            {
                return new ResponseEntity(ex.getMessage(), HttpStatus.UNAUTHORIZED);
            }
    }

    /**
     * Method to registration user
     * @param authDto
     * @return
     */
    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody AuthDto authDto)
    {

        UserDto userDto = new UserDto();
        userDto.setUsername(authDto.getUsername());
        userDto.setPassword(authDto.getPassword());
        userDto.setRoleName(authDto.getRoleName());

        System.out.println(userDto);
        System.out.println(authDto.getRoleName());
        System.out.println(userDto.getRoleName());
        System.out.println(userDto.getUsername());
        try
        {
            // Create user and return response
            userDto = userService.createUser(userDto);
            return ResponseEntity.ok().body(userDto);
        }
        catch (UserExistedException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
