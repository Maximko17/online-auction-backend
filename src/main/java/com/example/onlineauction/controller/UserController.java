package com.example.onlineauction.controller;

import com.example.onlineauction.dto.user.UserDto;
import com.example.onlineauction.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

//    @PutMapping
//    public UserDto update(@RequestPart("lot") UserDto dto,
//                          @RequestPart(value = "image", required = false) MultipartFile image) {
//        UserEntity user = userMapper.toEntity(dto);
//        UserEntity updatedUser = userService.update(user);
//        return userMapper.toDto(updatedUser);
//    }

//    @GetMapping("/{id}")
//    public UserDto getById(@PathVariable @Argument final Long id) {
//        User user = userService.getById(id);
//        return userMapper.toDto(user);
//    }
}
