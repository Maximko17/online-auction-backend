package com.example.onlineauction.repository.impl;

import com.example.onlineauction.dto.lot.LotListFiltersDto;
import com.example.onlineauction.dto.lot.LotListOrderDto;
import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.entity.QLotEntity;
import com.example.onlineauction.entity.QLotImageEntity;
import com.example.onlineauction.entity.QUserEntity;
import com.example.onlineauction.repository.LotCustomRepository;
import com.querydsl.core.QueryModifiers;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class LotCustomRepositoryImpl implements LotCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public List<LotEntity> findByFilters(LotListFiltersDto filters, LotListOrderDto order, long page, long limit) {
        JPAQuery<LotEntity> query = new JPAQuery<>(entityManager);
        QLotEntity lot = QLotEntity.lotEntity;
        QLotImageEntity lotImage = QLotImageEntity.lotImageEntity;
        QUserEntity seller = QUserEntity.userEntity;

        Predicate whereClause = filters == null ? null
                : QPredicate.builder()
                .add(filters.getSellerId(), seller.id::eq)
                .buildAnd();

        OrderSpecifier<?>[] orderClause = order == null ? null
                : QOrder.builder()
                .add(order.getUserId(), seller.id)
                .build();

        return query.from(lot)
                .innerJoin(lot.images, lotImage).fetchJoin()
                .innerJoin(lot.seller, seller).fetchJoin()
                .where(whereClause)
                .orderBy(orderClause)
                .restrict(new QueryModifiers(limit, page * limit))
                .fetch();
    }

}


