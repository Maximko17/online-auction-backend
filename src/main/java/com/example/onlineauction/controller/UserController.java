package com.example.onlineauction.controller;

import com.example.onlineauction.dto.lot.LotFullInfoDto;
import com.example.onlineauction.dto.lot.getLotList.GetLotListRequestDto;
import com.example.onlineauction.dto.lot.getLotList.GetLotListResponseDto;
import com.example.onlineauction.security.JwtUserDetails;
import com.example.onlineauction.service.LotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final LotService lotService;

    @PostMapping("/me/lots/tracking")
    public GetLotListResponseDto getTrackingLots(@Validated @RequestBody GetLotListRequestDto requestDto,
                                                 @AuthenticationPrincipal JwtUserDetails principal) {
        requestDto.getFilters().setTrackingUserId(principal.getUserEntity().getId());
        Page<LotFullInfoDto> listByFilters = lotService
                .getListByFilters(
                        requestDto.getFilters(),
                        requestDto.getOrder(),
                        requestDto.getPage(),
                        requestDto.getLimit()
                );
        return new GetLotListResponseDto(listByFilters.getContent(), listByFilters.getTotalPages());
    }

    @PostMapping("/me/bids/lots")
    public GetLotListResponseDto getBidsLots(@Validated @RequestBody GetLotListRequestDto requestDto,
                                             @AuthenticationPrincipal JwtUserDetails principal) {
        requestDto.getFilters().setBidUserId(principal.getUserEntity().getId());
        Page<LotFullInfoDto> listByFilters = lotService
                .getListByFilters(
                        requestDto.getFilters(),
                        requestDto.getOrder(),
                        requestDto.getPage(),
                        requestDto.getLimit()
                );
        return new GetLotListResponseDto(listByFilters.getContent(), listByFilters.getTotalPages());
    }
}
