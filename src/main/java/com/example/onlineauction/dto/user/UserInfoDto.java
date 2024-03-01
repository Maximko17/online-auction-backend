package com.example.onlineauction.dto.user;

import com.example.onlineauction.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private Short rating;
    private String image;
}