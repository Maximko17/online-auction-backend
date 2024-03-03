package com.example.onlineauction.service;

import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.entity.LotImageEntity;
import com.example.onlineauction.repository.LotImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LotImageService {

    private final S3Service s3Service;
    private final LotImageRepository lotImageRepository;

    public List<LotImageEntity> saveNew(List<MultipartFile> images, LotEntity lot) {
        List<LotImageEntity> lotImages = images.stream()
                .map(image -> {
                    LotImageEntity lotImageEntity = new LotImageEntity();
                    lotImageEntity.setImage(s3Service.upload(image));
                    lotImageEntity.setLot(lot);
                    return lotImageEntity;
                })
                .collect(Collectors.toList());
        return lotImageRepository.saveAll(lotImages);
    }

    public List<LotImageEntity> update(List<MultipartFile> images, LotEntity lot) {
        List<String> prevLotImagesNames = lot.getImages().stream()
                .map(LotImageEntity::getImage)
                .collect(Collectors.toList());
        lotImageRepository.deleteAllByLot(lot);
        s3Service.delete(prevLotImagesNames);

        return saveNew(images, lot);
    }
}
