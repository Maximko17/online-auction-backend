package com.example.onlineauction.service;

import com.example.onlineauction.dto.lot.LotListRequestDto;
import com.example.onlineauction.entity.*;
import com.example.onlineauction.exception.ResourceNotFoundException;
import com.example.onlineauction.repository.LotImageRepository;
import com.example.onlineauction.repository.LotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        lotEntity.setSeller(authUser);
        lotEntity.setStatus(Status.REVIEW);
        LotEntity savedLot = lotRepository.save(lotEntity);

        List<LotImageEntity> lotImages = images.stream()
                .map(image -> {
                    LotImageEntity lotImageEntity = new LotImageEntity();
                    lotImageEntity.setImage(imageService.upload(image));
                    lotImageEntity.setLotEntity(savedLot);
                    return lotImageEntity;
                })
                .collect(Collectors.toList());
        lotImageRepository.saveAll(lotImages);

        return savedLot.getId();
    }

    public LotEntity get(Long id) {
        return lotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lot not found."));
    }

    public List<LotEntity> getListByFilters(LotListRequestDto requestDto) {
        return lotRepository.findByFilters(
                requestDto.getFilters(),
                requestDto.getOrder(),
                requestDto.getPage(),
                requestDto.getLimit());
    }
}
