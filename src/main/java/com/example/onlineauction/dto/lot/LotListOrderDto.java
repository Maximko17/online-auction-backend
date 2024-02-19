package com.example.onlineauction.dto.lot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LotListOrderDto {

    private Order userId;

    public enum Order {
        ASC,
        DESC
    }
}
