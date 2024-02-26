package com.example.onlineauction.dto.bid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class BidsCountAndMaxBidDto {
    private BigDecimal lastBid;
    private long totalBids;
}
