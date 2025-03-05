package com.example.greetingapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
