package com.example.greetingapp.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserDTO {

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{2,}$", message = "First name should start with a capital letter and have at least 3 characters")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{2,}$", message = "Last name should start with a capital letter and have at least 3 characters")
    private String lastName;

    @NotBlank
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
