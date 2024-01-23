package com.example.onlineauction.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthLoginRequest {

    @NotNull(message = "Username must be not null.")
    private String username;

    @NotNull(message = "Password must be not null.")
    private String password;

}
