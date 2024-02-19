package com.example.onlineauction.repository;

import com.example.onlineauction.entity.LotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LotRepository extends JpaRepository<LotEntity, Long>, LotCustomRepository {

    @Query(value = "select e from LotEntity e join e.images where e.id = ?1 ")
    Optional<LotEntity> findJoinImagesById(Long id);

}
