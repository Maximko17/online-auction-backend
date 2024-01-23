package com.example.onlineauction.service;

import com.example.onlineauction.dto.auth.AuthLoginRequest;
import com.example.onlineauction.dto.auth.AuthLoginResponse;
import com.example.onlineauction.entity.UserEntity;
import com.example.onlineauction.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthLoginResponse login(AuthLoginRequest loginRequest) {
        AuthLoginResponse authLoginResponse = new AuthLoginResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserEntity user = userService.getByUsername(loginRequest.getUsername());
        authLoginResponse.setId(user.getId());
        authLoginResponse.setUsername(user.getName());
        authLoginResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(), user.getName(), user.getRoles()));
        authLoginResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(), user.getName()));
        return authLoginResponse;
    }

    public AuthLoginResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }

}
