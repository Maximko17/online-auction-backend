package com.example.onlineauction.dto.lot;

import com.example.onlineauction.dto.user.UserInfoDto;
import com.example.onlineauction.entity.Status;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class LotInfoDto {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal bidIncrement;
    private final BigDecimal startBid;
    private final BigDecimal lastBid;
    private final Integer totalBids;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Status status;
    private List<String> images;
    private final UserInfoDto seller;
    private final Boolean isTracking;
}
