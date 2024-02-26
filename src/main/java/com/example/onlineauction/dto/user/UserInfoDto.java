package com.example.onlineauction.dto.user;

import com.example.onlineauction.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private Short rating;
    private String image;
}