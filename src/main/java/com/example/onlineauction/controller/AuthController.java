package com.example.onlineauction.controller;

import com.example.onlineauction.dto.auth.*;
import com.example.onlineauction.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    public JwtResponseDto register(@Validated @RequestBody RegisterRequestDto registerRequestDto) {
        return authService.register(registerRequestDto);
    }

    @PostMapping("/send-register-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendRegisterEmail(@Validated @RequestBody EmailRequestDto emailRequestDto) {
        authService.sendRegisterEmail(emailRequestDto.getEmail());
    }

    @PostMapping("/send-reset-password-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendResetPasswordEmail(@Validated @RequestBody EmailRequestDto emailRequestDto) {
        authService.sendResetPasswordEmail(emailRequestDto.getEmail());
    }

    @PutMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@Validated @RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        authService.resetPassword(resetPasswordRequestDto);
    }

    @PostMapping("/refresh")
    public JwtResponseDto refresh(@RequestBody JwtRefreshRequestDto jwtResponseDto) {
        return authService.refresh(jwtResponseDto.getRefreshToken());
    }
}
