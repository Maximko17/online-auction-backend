package com.example.onlineauction.service;

import com.example.onlineauction.dto.lot.LotListFiltersDto;
import com.example.onlineauction.dto.lot.LotListOrderDto;
import com.example.onlineauction.dto.lot.getLotList.GetLotListRequestDto;
import com.example.onlineauction.entity.*;
import com.example.onlineauction.exception.ResourceNotFoundException;
import com.example.onlineauction.repository.LotImageRepository;
import com.example.onlineauction.repository.LotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotService {

    private static final Logger logger = LoggerFactory.getLogger(LotService.class);
    private final ImageService imageService;
    private final LotRepository lotRepository;
    private final LotImageRepository lotImageRepository;

    @Transactional
    public Long create(LotEntity lotEntity, List<MultipartFile> images, UserEntity authUser) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("TEST");
        lotEntity.setSeller(authUser);
        lotEntity.setStatus(Status.REVIEW);
        lotEntity.setCategory(categoryEntity);
        LotEntity savedLot = lotRepository.save(lotEntity);

        List<LotImageEntity> lotImages = images.stream()
                .map(image -> {
                    LotImageEntity lotImageEntity = new LotImageEntity();
                    lotImageEntity.setImage(imageService.upload(image));
                    lotImageEntity.setLot(savedLot);
                    return lotImageEntity;
                })
                .collect(Collectors.toList());
        lotImageRepository.saveAll(lotImages);

        return savedLot.getId();
    }

    @Transactional
    public LotEntity getFullLotInfo(Long id) {
        LotEntity lotEntity = lotRepository.findFullLotInfo(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lot not found."));

        if (lotEntity.getStatus().equals(Status.ACTIVE)
                && LocalDateTime.now().isAfter(lotEntity.getEndTime())) {
            lotEntity.setStatus(Status.CLOSED);
            lotRepository.save(lotEntity);
        }
        if (lotEntity.getStatus().equals(Status.NEW)
                && LocalDateTime.now().isAfter(lotEntity.getStartTime())) {
            lotEntity.setStatus(Status.ACTIVE);
            lotRepository.save(lotEntity);
        }

        return lotEntity;
    }

    public LotEntity getById(Long id) {
        return lotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lot not found."));
    }

    public Page<LotEntity> getListByFilters(LotListFiltersDto filters, LotListOrderDto order, Integer page, Integer limit) {
        return lotRepository.findByFilters(filters, order, page, limit);
    }

    public LotEntity update(LotEntity lotEntity) {
        if (lotEntity == null) {
            return null;
        }
        return lotRepository.save(lotEntity);
    }
}
