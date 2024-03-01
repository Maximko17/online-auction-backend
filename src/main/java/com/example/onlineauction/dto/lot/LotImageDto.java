package com.example.onlineauction.dto.lot;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LotImageDto {
    private Long id;
    private Long lotId;
    private String image;
}