package com.example.onlineauction.repository.impl;

import com.example.onlineauction.dto.category.CategoryEntityDto;
import com.example.onlineauction.repository.CategoryCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import liquibase.util.NumberUtil;
import lombok.AllArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<CategoryEntityDto> findAllForLotByLotId(Long lotId) {
        List<Tuple> resultTuples = entityManager.createNativeQuery("""
                WITH RECURSIVE tree AS (   
                       SELECT cat.id,   
                              cat.name,   
                              cat.parent_id   
                       FROM auction.categories cat   
                       WHERE cat.id = (SELECT l.category_id FROM auction.lots l WHERE l.id = :lotId)   
                       UNION ALL   
                       SELECT c.id,   
                              c.name,   
                              c.parent_id   
                       FROM auction.categories c   
                                INNER JOIN tree t ON t.parent_id = c.id   
                   )   
                   SELECT t.id as id,   
                          t.name as name ,   
                          t.parent_id as parentId   
                   FROM tree t   
                   ORDER BY t.id """, Tuple.class)
                .setParameter("lotId", lotId)
                .getResultList();


        List<CategoryEntityDto> collect = resultTuples.stream()
                .map(obj ->
                        new CategoryEntityDto(
                                (Long) obj.get("id"),
                                (String) obj.get("name"),
                                (Long) obj.get("parentId"),
                                0
                        )
                )
                .collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<CategoryEntityDto> findAllBetweenDepths(@Param("startDepth") int startDepth, @Param("endDepth") int endDepth) {
        List<Tuple> resultTuples = entityManager.createNativeQuery("""
                    WITH RECURSIVE tree (id, name, parent_id, depth) AS (  
                    SELECT c.id,  
                           c.name,  
                           c.parent_id,  
                           0  
                    FROM auction.categories c  
                    WHERE c.parent_id is null  
                    UNION ALL  
                    SELECT c.id,  
                           c.name,  
                           c.parent_id,  
                           t.depth + 1  
                    FROM auction.categories c  
                             INNER JOIN tree t ON t.id = c.parent_id  
                )  
                SELECT t.id as Id,  
                       t.name as name,  
                       t.parent_id as parentId,  
                       t.depth as depth  
                FROM tree t  
                where t.depth BETWEEN :startDepth AND :endDepth  
                ORDER BY t.depth 
                """, Tuple.class)
                .setParameter("startDepth", startDepth)
                .setParameter("endDepth", endDepth)
                .getResultList();

        List<CategoryEntityDto> collect = resultTuples.stream()
                .map(obj ->
                        new CategoryEntityDto(
                                (Long) obj.get("id"),
                                (String) obj.get("name"),
                                (Long) obj.get("parentId"),
                                (Integer) obj.get("depth")
                        )
                )
                .collect(Collectors.toList());

        return collect;
    }
}
