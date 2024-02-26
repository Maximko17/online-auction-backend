package com.example.onlineauction.dto.lot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LotListOrderDto {

    private Order lotId;

    public enum Order {
        ASC,
        DESC
    }
}
