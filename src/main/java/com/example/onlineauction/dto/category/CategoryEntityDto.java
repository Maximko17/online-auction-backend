package com.example.onlineauction.dto.category;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryEntityDto {
    private Long id;
    private String name;
    private Long parentId;
    private Integer depth;
    private List<CategoryEntityDto> childCategories;

    public CategoryEntityDto(Long id, String name, Long parentId, Integer depth) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.depth = depth;
        this.childCategories = new ArrayList<>();
    }
}
