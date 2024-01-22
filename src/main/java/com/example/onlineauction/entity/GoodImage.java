package com.example.onlineauction.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GoodImage {

    private MultipartFile file;

}
