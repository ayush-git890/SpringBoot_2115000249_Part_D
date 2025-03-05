package com.example.greetingapp.service;

import com.example.greetingapp.dto.AuthUserDTO;
import com.example.greetingapp.dto.LoginDTO;
import com.example.greetingapp.model.AuthUser;
import com.example.greetingapp.repository.AuthUserRepository;
import com.example.greetingapp.security.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthUserRepository authUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationService(AuthUserRepository authUserRepository, BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String registerUser(AuthUserDTO authUserDTO) {
        if (authUserRepository.existsByEmail(authUserDTO.getEmail())) {
            throw new RuntimeException("Email is already in use.");
        }

        AuthUser newUser = new AuthUser();
        newUser.setFirstName(authUserDTO.getFirstName());
        newUser.setLastName(authUserDTO.getLastName());
        newUser.setEmail(authUserDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(authUserDTO.getPassword()));

        authUserRepository.save(newUser);
        return "User registered successfully!";
    }

    public String loginUser(LoginDTO loginDTO) {
        AuthUser user = authUserRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password!");
        }

        return jwtTokenProvider.generateToken(user.getEmail());
    }
}
