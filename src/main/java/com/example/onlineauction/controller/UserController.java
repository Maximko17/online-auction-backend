package com.example.onlineauction.controller;

import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.entity.UserEntity;
import com.example.onlineauction.security.JwtUserDetails;
import com.example.onlineauction.service.BidService;
import com.example.onlineauction.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final TrackingService trackingService;
    private final BidService bidService;

    @GetMapping("/me/lots/tracking")
    public List<LotEntity> getTrackingLots(@AuthenticationPrincipal JwtUserDetails principal) {
        UserEntity user = principal.getUserEntity();
        return trackingService.getUserTrackingLots(user.getId());
    }

//    @GetMapping("/me/bids")
//    public List<LotEntity> getBids(@AuthenticationPrincipal JwtUserDetails principal) {
//        UserEntity user = principal.getUserEntity();
////        bidService.findAllByUserId(user.getId());
//        return ;
//    }
}
