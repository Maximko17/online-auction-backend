package com.example.onlineauction.dto.category;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryEntityDto {
    private Long id;
    private String name;
    private Long parentId;
    private List<CategoryEntityDto> child;

    public CategoryEntityDto(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.child = new ArrayList<>();
    }
}
