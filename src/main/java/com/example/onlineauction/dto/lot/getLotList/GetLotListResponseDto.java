package com.example.onlineauction.dto.lot.getLotList;

import com.example.onlineauction.dto.lot.LotFullInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetLotListResponseDto {
    List<LotFullInfoDto> content;
    int totalPages;
}
