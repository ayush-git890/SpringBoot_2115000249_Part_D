package com.example.greetingapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class LoginDTO {
    @NotBlank
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank
    private String password;

}

