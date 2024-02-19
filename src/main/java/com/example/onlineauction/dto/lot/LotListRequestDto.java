package com.example.onlineauction.dto.lot;

import lombok.Data;

@Data
public class LotListRequestDto {
    private LotListFiltersDto filters;
    private LotListOrderDto order;
    private int page;
    private int limit;
}
