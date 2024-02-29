package com.example.onlineauction.service;

import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.entity.TrackingEntity;
import com.example.onlineauction.entity.TrackingEntityPK;
import com.example.onlineauction.entity.UserEntity;
import com.example.onlineauction.exception.ConflictException;
import com.example.onlineauction.repository.TrackingRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrackingService {

    private final LotService lotService;
    private final TrackingRepository trackingRepository;

    @Transactional
    public void track(UserEntity userEntity, Long lotId) {
        boolean alreadyTracking = trackingRepository
                .existsById(new TrackingEntityPK(userEntity.getId(), lotId));
        if (alreadyTracking) {
            throw new ConflictException(
                    String.format("Lot %d already tracked by user %d", lotId, userEntity.getId()));
        }

        LotEntity lotEntity = lotService.getById(lotId);
        TrackingEntity trackingEntity = new TrackingEntity();
        trackingEntity.setTrackingEntityPK(new TrackingEntityPK(userEntity.getId(), lotEntity.getId()));
        trackingEntity.setLot(lotEntity);
        trackingEntity.setUser(userEntity);
        trackingRepository.save(trackingEntity);
    }

}
