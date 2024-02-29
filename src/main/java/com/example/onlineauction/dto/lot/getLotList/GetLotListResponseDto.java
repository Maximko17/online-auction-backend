package com.example.onlineauction.dto.lot.getLotList;

import com.example.onlineauction.dto.lot.LotInfoDto;
import com.example.onlineauction.entity.LotEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetLotListResponseDto {
    List<LotInfoDto> content;
    int totalPages;
}
