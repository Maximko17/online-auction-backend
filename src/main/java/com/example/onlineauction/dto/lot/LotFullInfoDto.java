package com.example.onlineauction.dto.lot;

import com.example.onlineauction.dto.user.UserInfoDto;
import com.example.onlineauction.entity.Status;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class LotFullInfoDto {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal bidIncrement;
    private final BigDecimal startBid;
    private final BigDecimal lastBid;
    private final Integer totalBids;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private Status status;
    private List<String> images;
    private final UserInfoDto seller;
    private final Boolean isTracking;

    public LotFullInfoDto(Long id, String name, String description, BigDecimal bidIncrement, BigDecimal startBid, BigDecimal lastBid, Integer totalBids, LocalDateTime startTime, LocalDateTime endTime, Status status, UserInfoDto seller, Boolean isTracking) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bidIncrement = bidIncrement;
        this.startBid = startBid;
        this.lastBid = lastBid;
        this.totalBids = totalBids;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.seller = seller;
        this.isTracking = isTracking;
    }
}
