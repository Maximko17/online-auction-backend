package com.example.onlineauction.repository;

import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LotRepository extends JpaRepository<LotEntity, Long>, LotCustomRepository {

    @Modifying
    @Query(nativeQuery = true,
            value = "update lots set status = cast(:status as status) where id = :id")
    int updateStatusById(@Param("status") String status, @Param("id") Long id);

    default int markAsActive(Long id) {
        return updateStatusById(Status.ACTIVE.name(), id);
    }
    default int markAsClosed(Long id) {
        return updateStatusById(Status.CLOSED.name(), id);
    }
}