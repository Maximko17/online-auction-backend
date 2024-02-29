package com.example.onlineauction.service;

import com.example.onlineauction.dto.bid.BidsCountAndMaxBidDto;
import com.example.onlineauction.entity.BidEntity;
import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.entity.UserEntity;
import com.example.onlineauction.repository.BidsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class BidService {

    private final ApplicationEventPublisher publisher;
    private final LotService lotService;
    private final BidsRepository bidsRepository;

    @Transactional
    public Long create(UserEntity authUser, Long lotId, BigDecimal bid) {
        LotEntity lotEntity = lotService.getById(lotId);
        if (bid.longValue() < lotEntity.getBidIncrement().longValue()) {
            throw new IllegalStateException("Bid increment is less then expected");
        }
        if (LocalDateTime.now().isAfter(lotEntity.getEndTime())) {
            throw new IllegalStateException("Auction is over");
        }

        BidEntity bidEntity = new BidEntity();
        bidEntity.setBid(bid);
        bidEntity.setLot(lotEntity);
        bidEntity.setBidTime(LocalDateTime.now());
        bidEntity.setUser(authUser);
        bidsRepository.save(bidEntity);

        lotEntity.setLastBid(bid);
        lotEntity.setTotalBids(lotEntity.getTotalBids() == null ? 0 : lotEntity.getTotalBids() + 1);
        lotService.update(lotEntity);

        publisher.publishEvent(bidEntity);
        return 1L;
    }

    public List<BidEntity> findAllByLotId(Long lotId) {
        return bidsRepository.findAllByLotId(lotId);
    }

    public List<BidEntity> getUserBidLots(Long userId) {
        List<BidEntity> userBids = bidsRepository.findAllByUserId(userId);
//        lotService.
        return userBids;
    }

}
