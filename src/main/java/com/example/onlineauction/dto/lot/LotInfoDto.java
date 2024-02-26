package com.example.onlineauction.dto.lot;

import com.example.onlineauction.dto.user.UserInfoDto;
import com.example.onlineauction.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LotInfoDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal bidIncrement;
    private BigDecimal startBid;
    private BigDecimal lastBid;
    private Long totalBids;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Status status;
    private List<ImageInfoDto> images;
    private UserInfoDto seller;

    @Getter
    @Setter
    public static class ImageInfoDto {
        private Long id;
        private String image;
    }
}