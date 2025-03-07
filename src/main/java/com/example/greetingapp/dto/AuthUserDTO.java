package com.example.greetingapp.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Data
public class AuthUserDTO {
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{2,}$", message = "your first name should start with a capital letter")
    private String firstName;

    @Getter
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{2,}$", message = "your last name should start with a capital letter")
    private String lastName;

    public String getFirstname() {
        return firstName;
    }

    @Getter
    @NotBlank
    @Email(message = "please enter a valid email")
    private String email;

    @Getter
    @NotBlank
    private String password;
}