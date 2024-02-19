package com.example.onlineauction.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VerifyEmailRequestDto {
    @NotEmpty
    private String email;
}
