package com.example.onlineauction.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailRequestDto {
    @NotEmpty
    private String email;
}
