package com.example.onlineauction.service;

import com.example.onlineauction.dto.auth.JwtResponseDto;
import com.example.onlineauction.dto.auth.LoginRequestDto;
import com.example.onlineauction.dto.auth.RegisterRequestDto;
import com.example.onlineauction.entity.UserEntity;
import com.example.onlineauction.exception.AccessDeniedException;
import com.example.onlineauction.exception.ConflictException;
import com.example.onlineauction.exception.UnauthorizedException;
import com.example.onlineauction.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final MailService mailService;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponseDto login(LoginRequestDto loginRequest) {
        UserEntity user;
        try {
            user = userService.getByEmail(loginRequest.getEmail());
        } catch (Exception e) {
            throw new UnauthorizedException("Bad login or password");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        return new JwtResponseDto()
                .setAccessToken(jwtTokenProvider.createAccessToken(user))
                .setRefreshToken(jwtTokenProvider.createRefreshToken(user));
    }

    public JwtResponseDto register(RegisterRequestDto registerRequestDto) {
        if (!jwtTokenProvider.validateToken(registerRequestDto.getToken())) {
            throw new AccessDeniedException();
        }
        String tokenEmail = jwtTokenProvider.getEmail(registerRequestDto.getToken());
        if (!tokenEmail.equals(registerRequestDto.getEmail())) {
            throw new IllegalStateException(
                    "Email do not match."
            );
        }
        if (userService.existsByEmail(tokenEmail)) {
            throw new ConflictException(String.format("User with email '%s' already exists", tokenEmail));
        }
        if (!registerRequestDto.getPassword().equals(registerRequestDto.getPasswordConfirmation())) {
            throw new IllegalStateException(
                    "Password and password confirmation do not match."
            );
        }
        UserEntity user = userService.create(registerRequestDto.getEmail(), registerRequestDto.getUsername(), registerRequestDto.getPassword());
        return new JwtResponseDto()
                .setAccessToken(jwtTokenProvider.createAccessToken(user))
                .setRefreshToken(jwtTokenProvider.createRefreshToken(user));
    }

    public void sendVerificationEmail(String email) {
        Optional<UserEntity> user = userService.findByEmail(email);
        if (user.isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        String emailVerificationToken = jwtTokenProvider.createEmailVerificationToken(email);
        mailService.sendRegistrationEmail(email, emailVerificationToken);
    }

    public JwtResponseDto refresh(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new AccessDeniedException();
        }
        Long userId = Long.valueOf(jwtTokenProvider.getId(refreshToken));
        UserEntity user = userService.getById(userId);
        return new JwtResponseDto()
                .setAccessToken(jwtTokenProvider.createAccessToken(user));
    }
}
