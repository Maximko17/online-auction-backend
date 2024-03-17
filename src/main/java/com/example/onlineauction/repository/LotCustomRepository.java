package com.example.onlineauction.repository;

import com.example.onlineauction.dto.lot.LotFullInfoDto;
import com.example.onlineauction.dto.lot.LotListFiltersDto;
import com.example.onlineauction.dto.lot.LotListOrderDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface LotCustomRepository {
    Page<LotFullInfoDto> findByFilters(LotListFiltersDto filters, LotListOrderDto sort, Integer page, Integer limit);

    Optional<LotFullInfoDto> findFullLotInfo(Long id);
}
