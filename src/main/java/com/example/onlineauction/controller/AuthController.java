package com.example.onlineauction.controller;

import com.example.onlineauction.dto.auth.*;
import com.example.onlineauction.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponseDto login(@Validated @RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/register")
    public JwtResponseDto register(@Validated @RequestBody RegisterRequestDto registerRequestDto) {
        return authService.register(registerRequestDto);
    }

    @PostMapping("/verification")
    public ResponseEntity<Void> verifyEmail(@Validated @RequestBody VerifyEmailRequestDto verifyEmailRequestDto) {
        authService.sendVerificationEmail(verifyEmailRequestDto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public JwtResponseDto refresh(@RequestBody JwtRefreshRequestDto jwtResponseDto) {
        return authService.refresh(jwtResponseDto.getRefreshToken());
    }
}
