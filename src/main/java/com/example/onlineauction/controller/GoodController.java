package com.example.onlineauction.controller;

import com.example.onlineauction.entity.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/goods")
public class GoodController {

    @GetMapping
    public String update(@AuthenticationPrincipal Object user) {
        System.out.println(user);
        return "work";
    }

}
