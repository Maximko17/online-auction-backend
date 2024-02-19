package com.example.onlineauction.repository;

import com.example.onlineauction.dto.lot.LotListFiltersDto;
import com.example.onlineauction.dto.lot.LotListOrderDto;
import com.example.onlineauction.entity.LotEntity;

import java.util.List;

public interface LotCustomRepository {
    List<LotEntity> findByFilters(LotListFiltersDto filters, LotListOrderDto sort, long page, long limit);
}
