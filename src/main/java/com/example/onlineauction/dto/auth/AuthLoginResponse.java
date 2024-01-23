package com.example.onlineauction.dto.auth;

import lombok.Data;

@Data
public class AuthLoginResponse {

    private Long id;
    private String username;
    private String accessToken;
    private String refreshToken;

}
