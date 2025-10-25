package com.dilvercb.appointment_reservation_backend.controller;

import com.dilvercb.appointment_reservation_backend.dto.AuthCreateUserRequest;
import com.dilvercb.appointment_reservation_backend.dto.AuthLoginRequest;
import com.dilvercb.appointment_reservation_backend.dto.AuthResponse;
import com.dilvercb.appointment_reservation_backend.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthCreateUserRequest authCreateUserRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetailsService.createUser(authCreateUserRequest));
    }
    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login (@Valid @RequestBody AuthLoginRequest authLoginRequest){
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsService.loginUser(authLoginRequest));
    }
}
