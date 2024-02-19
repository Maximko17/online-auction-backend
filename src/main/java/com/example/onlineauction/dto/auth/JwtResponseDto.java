package com.example.onlineauction.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JwtResponseDto {
    private String accessToken;
    private String refreshToken;
}
