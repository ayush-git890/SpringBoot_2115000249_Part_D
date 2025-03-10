package com.example.greetingapp.controller;

import com.example.greetingapp.dto.AuthUserDTO;
import com.example.greetingapp.dto.LoginDTO;
import com.example.greetingapp.dto.PasswordUpdateDTO;
import com.example.greetingapp.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authService;

    @Autowired
    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthUserDTO userDTO) {
        return ResponseEntity.ok(authService.register(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        String response = authService.login(loginDTO);
        if (response.startsWith("Login successful! Token: ")) {
            return ResponseEntity.ok(Map.of("message", "Login successful!", "token", response.split(": ")[1]));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", response));
        }
    }

    // ✅ Forgot Password Endpoint
    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable String email, @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        String response = authService.forgotPassword(email, passwordUpdateDTO.getPassword());
        return ResponseEntity.ok(Map.of("message", response));
    }

    // ✅ Reset Password Endpoint (For Logged-in Users)
    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<?> resetPassword(
            @PathVariable String email,
            @RequestParam String currentPassword,
            @RequestParam String newPassword
    ) {
        String response = authService.resetPassword(email, currentPassword, newPassword);
        return ResponseEntity.ok(Map.of("message", response));
    }
}