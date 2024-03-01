package com.example.onlineauction.repository;

import com.example.onlineauction.dto.lot.LotInfoDto;
import com.example.onlineauction.dto.lot.LotListFiltersDto;
import com.example.onlineauction.dto.lot.LotListOrderDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface LotCustomRepository {
    Page<LotInfoDto> findByFilters(LotListFiltersDto filters, LotListOrderDto sort, Integer page, Integer limit);

    Optional<LotInfoDto> findFullLotInfo(Long id);
}
