package com.example.greetingapp.service;

import com.example.greetingapp.dto.AuthUserDTO;
import com.example.greetingapp.dto.LoginDTO;
import com.example.greetingapp.model.AuthUser;
import com.example.greetingapp.repository.AuthUserRepository;
import com.example.greetingapp.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private AuthUserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public String register(AuthUserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return "Email is already in use.";
        }

        AuthUser user = new AuthUser();
        user.setFirstname(userDTO.getFirstName());
        user.setLastname(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);

        emailService.sendEmail(user.getEmail(), "Welcome to Greeting App!", "Thanks for registering!");

        return "User registered successfully!";
    }

    public String login(LoginDTO loginDTO) {
        Optional<AuthUser> userOptional = userRepository.findByEmail(loginDTO.getEmail());
        if (userOptional.isEmpty()) {
            return "User not found!";
        }

        AuthUser user = userOptional.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return "Invalid email or password!";
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return "Login successful! Token: " + token;
    }

    // ✅ Forgot Password Implementation
    @Transactional
    public String forgotPassword(String email, String newPassword) {
        Optional<AuthUser> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return "User not found with email: " + email;
        }

        AuthUser user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Send Confirmation Email
        emailService.sendEmail(user.getEmail(), "Password Reset Confirmation", "Your password has been successfully updated.");

        return "Password has been changed successfully!";
    }

    // ✅ Reset Password Implementation (For Logged-in Users)
    @Transactional
    public String resetPassword(String email, String currentPassword, String newPassword) {
        Optional<AuthUser> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return "User not found with email: " + email;
        }

        AuthUser user = userOptional.get();

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return "Current password is incorrect!";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Password reset successfully!";
    }
}