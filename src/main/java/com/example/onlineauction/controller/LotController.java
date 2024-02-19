package com.example.onlineauction.controller;

import com.example.onlineauction.dto.lot.CreateLotRequestDto;
import com.example.onlineauction.dto.lot.CreateLotResponseDto;
import com.example.onlineauction.dto.lot.LotListRequestDto;
import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.security.JwtUserDetails;
import com.example.onlineauction.service.LotService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lots")
@RequiredArgsConstructor
public class LotController {

    private final LotService lotService;
    private final ModelMapper modelMapper;

    @PostMapping
    public List<LotEntity> getLotList(@RequestBody LotListRequestDto lotListRequestDto) {
        return lotService.getListByFilters(lotListRequestDto);
    }

    @PostMapping(path = "/new", consumes = "multipart/form-data")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateLotResponseDto createNewLot(@Validated @RequestPart("lot") CreateLotRequestDto createLotRequestDto,
                                             @RequestPart("image") List<MultipartFile> images,
                                             @AuthenticationPrincipal JwtUserDetails principal) {
        LotEntity lot = modelMapper.map(createLotRequestDto, LotEntity.class);
        Long lotId = lotService.create(lot, images, principal.getUserEntity());
        return new CreateLotResponseDto().setId(lotId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LotEntity> getLot(@PathVariable Long id) {
        LotEntity lotEntity = lotService.get(id);
        return new ResponseEntity<>(lotEntity, HttpStatus.OK);
    }

}
