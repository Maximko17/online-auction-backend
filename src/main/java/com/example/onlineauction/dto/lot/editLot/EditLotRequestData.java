package com.example.onlineauction.dto.lot.editLot;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EditLotRequestData {
//    @NotEmpty
    private Long categoryId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private BigDecimal bidIncrement;
    @NotNull
    private BigDecimal startBid;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull
    private LocalDateTime endTime;
}
