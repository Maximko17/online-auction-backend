package com.example.onlineauction.repository;

import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.entity.LotImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotImageRepository extends JpaRepository<LotImageEntity, Long> {

    void deleteAllByLot(LotEntity lot);
}
