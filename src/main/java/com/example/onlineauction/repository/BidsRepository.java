package com.example.onlineauction.repository;

import com.example.onlineauction.dto.bid.BidsCountAndMaxBidDto;
import com.example.onlineauction.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BidsRepository extends JpaRepository<BidEntity, Long> {

    @Query(value = "select e from BidEntity e where e.lot.id = ?1 order by e.id desc")
    List<BidEntity> findAllByLotId(Long lotId);

    @Query(value = "select e from BidEntity e where e.user.id = ?1 order by e.id desc")
    List<BidEntity> findAllByUserId(Long lotId);

    @Query(value = "select new com.example.onlineauction.dto.bid.BidsCountAndMaxBidDto(" +
            "max(b.bid)," +
            "count(b.id))" +
            "from BidEntity b " +
            "where b.lot.id = ?1 " +
            "group by b.lot.id")
    Optional<BidsCountAndMaxBidDto> findCountAndMaxBidByLotId(Long lotId);

}
