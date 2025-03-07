package com.example.greetingapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordUpdateDTO {

    @NotBlank(message = "Password cannot be empty")
    private String password;
}