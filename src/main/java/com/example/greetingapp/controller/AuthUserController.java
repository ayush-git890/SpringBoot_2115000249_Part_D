package com.example.greetingapp.controller;

import com.example.greetingapp.dto.AuthUserDTO;
import com.example.greetingapp.dto.LoginDTO;
import com.example.greetingapp.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    private final AuthenticationService authenticationService;

    public AuthUserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthUserDTO authUserDTO) {
        return ResponseEntity.status(201).body(authenticationService.registerUser(authUserDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authenticationService.loginUser(loginDTO));
    }
}
