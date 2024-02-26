package com.example.onlineauction.repository;

import com.example.onlineauction.entity.LotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotRepository extends JpaRepository<LotEntity, Long>, LotCustomRepository {

}
