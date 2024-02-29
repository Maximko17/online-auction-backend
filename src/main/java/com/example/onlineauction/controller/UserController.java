package com.example.onlineauction.controller;

import com.example.onlineauction.dto.lot.LotInfoDto;
import com.example.onlineauction.dto.lot.getLotList.GetLotListRequestDto;
import com.example.onlineauction.dto.lot.getLotList.GetLotListResponseDto;
import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.security.JwtUserDetails;
import com.example.onlineauction.service.LotService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final LotService lotService;
    private final ModelMapper modelMapper;

    @PostMapping("/me/lots/tracking")
    public GetLotListResponseDto getTrackingLots(@Validated @RequestBody GetLotListRequestDto requestDto,
                                                 @AuthenticationPrincipal JwtUserDetails principal) {
        requestDto.getFilters().setTrackingUserId(principal.getUserEntity().getId());
        Page<LotEntity> lotEntities = lotService
                .getListByFilters(
                        requestDto.getFilters(),
                        requestDto.getOrder(),
                        requestDto.getPage(),
                        requestDto.getLimit()
                );
        List<LotInfoDto> lotInfoDtos = modelMapper
                .map(lotEntities.getContent(), new TypeToken<List<LotInfoDto>>() {}.getType());

        return new GetLotListResponseDto(lotInfoDtos, lotEntities.getTotalPages());
    }

    @PostMapping("/me/bids/lots")
    public GetLotListResponseDto getBidsLots(@Validated @RequestBody GetLotListRequestDto requestDto,
                                             @AuthenticationPrincipal JwtUserDetails principal) {
        requestDto.getFilters().setBidUserId(principal.getUserEntity().getId());
        Page<LotEntity> lotEntities = lotService
                .getListByFilters(
                        requestDto.getFilters(),
                        requestDto.getOrder(),
                        requestDto.getPage(),
                        requestDto.getLimit()
                );
        List<LotInfoDto> lotInfoDtos = modelMapper
                .map(lotEntities.getContent(), new TypeToken<List<LotInfoDto>>() {}.getType());

        return new GetLotListResponseDto(lotInfoDtos, lotEntities.getTotalPages());
    }
}
