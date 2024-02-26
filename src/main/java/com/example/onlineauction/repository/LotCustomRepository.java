package com.example.onlineauction.repository;

import com.example.onlineauction.dto.lot.LotListFiltersDto;
import com.example.onlineauction.dto.lot.LotListOrderDto;
import com.example.onlineauction.entity.LotEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface LotCustomRepository {
    Page<LotEntity> findByFilters(LotListFiltersDto filters, LotListOrderDto sort, int page, int limit);

    Optional<LotEntity> findFullLotInfo(Long id);
}
