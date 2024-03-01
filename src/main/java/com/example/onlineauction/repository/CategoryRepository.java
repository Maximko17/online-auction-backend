package com.example.onlineauction.repository;

import com.example.onlineauction.dto.category.CategoryEntityDto;
import com.example.onlineauction.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, CategoryCustomRepository {

//    @Query("select e.id as id, e.name as name, e.parentCategory.id as parentId " +
//            "from CategoryEntity e " +
//            "where e.parentCategory is null")
//    List<CategoryEntityDto> findAllRoot();
//
//    @Query("select e.id as id, e.name as name, e.parentCategory.id as parentId " +
//            "from CategoryEntity e " +
//            "where e.parentCategory.id = ?1")
//    List<CategoryEntityDto> findAllByParentId(Long parentId);


}
