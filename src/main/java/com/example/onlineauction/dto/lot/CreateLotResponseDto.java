package com.example.onlineauction.dto.lot;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateLotResponseDto {
    private Long id;
}
