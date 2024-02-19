package com.example.onlineauction.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String username;
    @NotEmpty
    private String passwordConfirmation;
    @NotEmpty
    private String token;
}

