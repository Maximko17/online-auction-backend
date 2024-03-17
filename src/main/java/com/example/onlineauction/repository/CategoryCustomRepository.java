package com.example.onlineauction.repository;

import com.example.onlineauction.dto.category.CategoryEntityDto;

import java.util.List;

public interface CategoryCustomRepository {
    List<CategoryEntityDto> findAllForLotByLotId(Long lotId);
    List<CategoryEntityDto> findAllBetweenDepths(int startDepth, int endDepth);
    List<CategoryEntityDto> findCategoryTree(Long id);
}
