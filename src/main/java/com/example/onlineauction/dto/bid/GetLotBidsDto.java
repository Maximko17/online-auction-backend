package com.example.onlineauction.dto.bid;

import com.example.onlineauction.dto.user.UserInfoDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class GetLotBidsDto {
    private Long id;
    private UserInfoDto user;
    private BigDecimal bid;
    private LocalDateTime bidTime;
}
