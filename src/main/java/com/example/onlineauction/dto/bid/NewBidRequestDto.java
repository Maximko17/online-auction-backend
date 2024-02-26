package com.example.onlineauction.dto.bid;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewBidRequestDto {
    private BigDecimal bid;
}
