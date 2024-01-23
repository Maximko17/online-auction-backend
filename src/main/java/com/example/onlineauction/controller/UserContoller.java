package com.example.onlineauction.controller;

import com.example.onlineauction.dto.validation.OnUpdate;
import com.example.onlineauction.exception.ResourceNotFoundException;
import com.example.onlineauction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1/users")
@RequiredArgsConstructor
public class UserContoller {

//    @PutMapping
//    public UserDto update(@Validated(OnUpdate.class)
//                          @RequestBody final UserDto dto) {
//        User user = userMapper.toEntity(dto);
//        User updatedUser = userService.update(user);
//        return userMapper.toDto(updatedUser);
//    }
//
//    @GetMapping("/{id}")
//    public UserDto getById(@PathVariable @Argument final Long id) {
//        User user = userService.getById(id);
//        return userMapper.toDto(user);
//    }
}
