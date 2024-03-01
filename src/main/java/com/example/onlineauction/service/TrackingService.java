package com.example.onlineauction.service;

import com.example.onlineauction.entity.*;
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
                    String.format("Lot %d already tracking", lotId));
        }
        LotEntity lotEntity = lotService.getById(lotId);
        if (lotEntity.getStatus().equals(Status.CLOSED)) {
            throw new ConflictException(
                    String.format("Lot %d closed", lotId));
        }
        TrackingEntity trackingEntity = new TrackingEntity();
        trackingEntity.setTrackingEntityPK(new TrackingEntityPK(userEntity.getId(), lotEntity.getId()));
        trackingEntity.setLot(lotEntity);
        trackingEntity.setUser(userEntity);
        trackingRepository.save(trackingEntity);
    }

    @Transactional
    public void deleteTrack(UserEntity userEntity, Long lotId) {
        trackingRepository
                .deleteById(new TrackingEntityPK(userEntity.getId(), lotId));
    }

}
