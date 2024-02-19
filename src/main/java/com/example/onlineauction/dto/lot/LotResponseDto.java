package com.example.onlineauction.dto.lot;

import com.example.onlineauction.entity.LotGroupEntity;
import com.example.onlineauction.entity.LotImageEntity;
import com.example.onlineauction.entity.Status;
import com.example.onlineauction.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class LotResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal bidIncrement;
    private BigDecimal startBid;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Status status;
    private LotGroupEntity lotsGroupEntity;
    private List<LotImageEntity> lotImages;
    private UserEntity sellerEntity;
}
