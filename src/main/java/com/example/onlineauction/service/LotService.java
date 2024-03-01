package com.example.onlineauction.service;

import com.example.onlineauction.dto.lot.LotInfoDto;
import com.example.onlineauction.dto.lot.LotListFiltersDto;
import com.example.onlineauction.dto.lot.LotListOrderDto;
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

    public Page<LotInfoDto> getListByFilters(LotListFiltersDto filters, LotListOrderDto order, Integer page, Integer limit) {
       return lotRepository.findByFilters(filters, order, page, limit);
    }

    @Transactional
    public Long create(LotEntity lotEntity, List<MultipartFile> images, UserEntity authUser) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("TEST");
        lotEntity.setSeller(authUser);
        lotEntity.setStatus(Status.NEW);
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
    public LotInfoDto getFullLotInfo(Long id) {
        LotInfoDto lotInfoDto = lotRepository.findFullLotInfo(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lot not found."));

        if (LocalDateTime.now().isAfter(lotInfoDto.getStartTime())
                && LocalDateTime.now().isBefore(lotInfoDto.getEndTime())
                && !lotInfoDto.getStatus().equals(Status.ACTIVE)) {
            lotRepository.markAsActive(lotInfoDto.getId());
        } else if (LocalDateTime.now().isAfter(lotInfoDto.getEndTime())
                && !lotInfoDto.getStatus().equals(Status.CLOSED)) {
            lotRepository.markAsClosed(lotInfoDto.getId());
        }

        return lotInfoDto;
    }

    public LotEntity getById(Long id) {
        return lotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lot not found."));
    }

    public LotEntity update(LotEntity lotEntity) {
        if (lotEntity == null) {
            return null;
        }
        return lotRepository.save(lotEntity);
    }

    public boolean isClosedById(Long lotId) {
        LotEntity lot = this.getById(lotId);
        return lot.getStatus().equals(Status.CLOSED);
    }
}
