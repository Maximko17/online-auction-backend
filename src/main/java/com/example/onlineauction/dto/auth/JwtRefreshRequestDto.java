package com.example.onlineauction.dto.auth;

import lombok.Data;

@Data
public class JwtRefreshRequestDto {
    private String refreshToken;
}
