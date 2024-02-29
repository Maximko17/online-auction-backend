package com.example.onlineauction.dto.lot.getLotList;

import com.example.onlineauction.dto.lot.LotListFiltersDto;
import com.example.onlineauction.dto.lot.LotListOrderDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetLotListRequestDto {
    private LotListFiltersDto filters;
    private LotListOrderDto order;
    @NotNull
    private Integer page;
    @NotNull
    private Integer limit;
}
