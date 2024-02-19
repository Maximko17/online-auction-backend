package com.example.onlineauction.service;

import com.example.onlineauction.entity.Role;
import com.example.onlineauction.entity.UserEntity;
import com.example.onlineauction.exception.ResourceNotFoundException;
import com.example.onlineauction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserEntity getById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Transactional(readOnly = true)
    public UserEntity getByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public UserEntity update(final UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Transactional
    public UserEntity create(String email, String username, String password) {
        UserEntity user = new UserEntity();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.ROLE_USER);
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

}
