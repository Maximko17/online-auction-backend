package com.example.onlineauction.repository;

import com.example.onlineauction.entity.TrackingEntity;
import com.example.onlineauction.entity.TrackingEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRepository extends JpaRepository<TrackingEntity, TrackingEntityPK> {
}
