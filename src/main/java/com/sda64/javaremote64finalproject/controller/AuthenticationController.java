package com.sda64.javaremote64finalproject.controller;

import com.sda64.javaremote64finalproject.dto.AuthenticationResponse;
import com.sda64.javaremote64finalproject.dto.LoginRequest;
import com.sda64.javaremote64finalproject.dto.RegisterRequest;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.exception.InvalidBodyException;
import com.sda64.javaremote64finalproject.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            authenticationService.register(registerRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) throws EntityNotFoundException {
            return ResponseEntity.ok(authenticationService.authenticate(loginRequest));

    }

    @ExceptionHandler(value = {EntityNotFoundException.class, IOException.class})
    ResponseEntity<Object> handleIllegalRequests(EntityNotFoundException ex, IOException e) {
        return new ResponseEntity<>("Bad request", HttpStatus.NOT_ACCEPTABLE);
    }
}