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

//    public List<CategoryEntityDto> findAllRoot() {
//        return categoryRepository.findAllRoot();
//    }
//
//    public List<CategoryEntityDto> findAllByParentId(Long parentId) {
//        return categoryRepository.findAllByParentId(parentId);
//    }
    public List<CategoryEntityDto> findAllBetweenDepths(int startDepth, int endDepth) {
        List<CategoryEntityDto> allBetweenDepths = categoryRepository.findAllBetweenDepths(startDepth, endDepth);

        // Сначала создадим Map для быстрого доступа к категориям по ID
        Map<Long, CategoryEntityDto> categoryMap = new HashMap<>();
        for (CategoryEntityDto category : allBetweenDepths) {
            categoryMap.put(category.getId(), category);
        }

        List<CategoryEntityDto> response = new ArrayList<>();
        // Теперь построим иерархию категорий
        for (CategoryEntityDto category : allBetweenDepths) {
            if (category.getParentId() != null) {
                CategoryEntityDto parent = categoryMap.get(category.getParentId());
                if (parent != null) {
                    parent.getChildCategories().add(category);
                    continue;
                }
            }
            response.add(category);
        }

        return response;
    }

    public List<CategoryEntityDto> findAllByLotId(Long lotId) {
        return categoryRepository.findAllForLotByLotId(lotId);
    }

//    public List<CategoryEntityDto> addNew() {
//        return categoryRepository.findAllByLotId(lotId);
//    }
}
