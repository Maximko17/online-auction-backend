package com.example.onlineauction.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ResetPasswordRequestDto {
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordConfirmation;
    @NotEmpty
    private String token;
}
