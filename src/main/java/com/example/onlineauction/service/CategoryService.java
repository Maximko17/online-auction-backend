package com.example.onlineauction.service;

import com.example.onlineauction.dto.category.CategoryEntityDto;
import com.example.onlineauction.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryEntityDto> findTree(Long id) {
        List<CategoryEntityDto> firstSubcategoriesFor = categoryRepository.findCategoryTree(id);
        return transformForResponse(firstSubcategoriesFor);
    }

    public List<CategoryEntityDto> findRootCategories() {
        List<CategoryEntityDto> rootCategories = categoryRepository.findAllRoot();
        return transformForResponse(rootCategories);
    }

    public List<CategoryEntityDto> findAllBetweenDepths(int startDepth, int endDepth) {
        List<CategoryEntityDto> categoryEntityDtos = categoryRepository.findAllBetweenDepths(startDepth, endDepth);
        return transformForResponse(categoryEntityDtos);
    }

    public List<CategoryEntityDto> findAllByLotId(Long lotId) {
        List<CategoryEntityDto> lotCategories = categoryRepository.findAllForLotByLotId(lotId);
        return transformForResponse(lotCategories);
    }

    private List<CategoryEntityDto> transformForResponse(List<CategoryEntityDto> categoryEntityDtos) {
        // Сначала создадим Map для быстрого доступа к категориям по ID
        Map<Long, CategoryEntityDto> categoryMap = new HashMap<>();
        for (CategoryEntityDto category : categoryEntityDtos) {
            categoryMap.put(category.getId(), category);
        }

        List<CategoryEntityDto> response = new ArrayList<>();
        // Теперь построим иерархию категорий
        for (CategoryEntityDto category : categoryEntityDtos) {
            if (category.getParentId() != null) {
                CategoryEntityDto parent = categoryMap.get(category.getParentId());
                if (parent != null) {
                    parent.getChild().add(category);
                    continue;
                }
            }
            response.add(category);
        }
        return response;
    }
}
