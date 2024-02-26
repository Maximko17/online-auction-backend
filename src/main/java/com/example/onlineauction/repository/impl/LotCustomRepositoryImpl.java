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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class LotCustomRepositoryImpl implements LotCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public Page<LotEntity> findByFilters(LotListFiltersDto filters, LotListOrderDto order, int page, int limit) {
        QLotEntity lot = QLotEntity.lotEntity;
        QLotImageEntity lotImage = QLotImageEntity.lotImageEntity;
        QUserEntity seller = QUserEntity.userEntity;

        Predicate whereClause = filters == null ? null
                : QPredicate.builder()
                .add(filters.getSellerId(), lot.seller.id::eq)
                .buildAnd();

        OrderSpecifier<?>[] orderClause = order == null ? new OrderSpecifier<?>[]{}
                : QOrder.builder()
                .add(order.getLotId(), lot.id)
                .build();

        Long totalCount = new JPAQuery<>(entityManager)
                .select(lot.id.count())
                .from(lot)
                .where(whereClause)
                .fetchOne();

        var filterQuery = new JPAQuery<>(entityManager)
                .select(lot.id)
                .from(lot)
                .where(whereClause)
                .restrict(new QueryModifiers((long) limit, (long) (page - 1) * limit))
                .fetch();

        List<LotEntity> content = new JPAQuery<>(entityManager)
                .select(lot)
                .from(lot)
                .innerJoin(lot.images, lotImage).fetchJoin()
                .innerJoin(lot.seller, seller).fetchJoin()
                .where(lot.id.in(filterQuery))
                .orderBy(orderClause)
                .fetch();

        return new PageImpl<>(content, PageRequest.of(page - 1, limit), totalCount == null ? 0 : totalCount);
    }

    @Override
    public Optional<LotEntity> findFullLotInfo(Long lotId) {
        QLotEntity lot = QLotEntity.lotEntity;
        QLotImageEntity lotImage = QLotImageEntity.lotImageEntity;
        QUserEntity seller = QUserEntity.userEntity;

        return Optional.ofNullable(new JPAQuery<>(entityManager)
                .select(lot)
                .from(lot)
                .innerJoin(lot.images, lotImage).fetchJoin()
                .innerJoin(lot.seller, seller).fetchJoin()
                .where(lot.id.eq(lotId))
                .fetchOne());
    }
}


