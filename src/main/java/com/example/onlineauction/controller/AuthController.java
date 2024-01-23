package com.example.onlineauction.controller;

import com.example.onlineauction.dto.auth.AuthLoginRequest;
import com.example.onlineauction.dto.auth.AuthLoginResponse;
import com.example.onlineauction.dto.auth.AuthRegisterRequest;
import com.example.onlineauction.dto.auth.AuthRegisterResponse;
import com.example.onlineauction.dto.validation.OnCreate;
import com.example.onlineauction.entity.UserEntity;
import com.example.onlineauction.service.AuthService;
import com.example.onlineauction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public AuthLoginResponse login(@Validated @RequestBody AuthLoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public AuthRegisterResponse register(@Validated(OnCreate.class) @RequestBody AuthRegisterRequest userDto) {
        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        UserEntity createdUser = userService.create(user);
        return modelMapper.map(createdUser, AuthRegisterResponse.class);
    }

    @PostMapping("/refresh")
    public AuthLoginResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
