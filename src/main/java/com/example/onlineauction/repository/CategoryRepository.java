package com.example.onlineauction.repository;

import com.example.onlineauction.dto.category.CategoryEntityDto;
import com.example.onlineauction.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, CategoryCustomRepository {

    @Query(value = "select new com.example.onlineauction.dto.category.CategoryEntityDto(" +
            "e.id," +
            "e.name, " +
            "e.parent.id) " +
            "from CategoryEntity e " +
            "where e.parent is null")
    List<CategoryEntityDto> findAllRoot();
}
