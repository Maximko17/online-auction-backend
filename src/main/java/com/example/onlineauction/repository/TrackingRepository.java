package com.example.onlineauction.repository;

import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.entity.TrackingEntity;
import com.example.onlineauction.entity.TrackingEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrackingRepository extends JpaRepository<TrackingEntity, TrackingEntityPK> {

    @Query(value = "select e.lot from TrackingEntity e where e.user.id = ?1")
    List<LotEntity> findAllLotsByUserId(Long userId);
}
