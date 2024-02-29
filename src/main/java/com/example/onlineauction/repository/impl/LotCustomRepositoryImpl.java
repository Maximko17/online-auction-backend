package com.example.onlineauction.repository.impl;

import com.example.onlineauction.dto.lot.LotListFiltersDto;
import com.example.onlineauction.dto.lot.LotListOrderDto;
import com.example.onlineauction.entity.*;
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

    public Page<LotEntity> findByFilters(LotListFiltersDto filters, LotListOrderDto order, Integer page, Integer limit) {
        QLotEntity lot = QLotEntity.lotEntity;
        QLotImageEntity lotImage = QLotImageEntity.lotImageEntity;
        QUserEntity seller = QUserEntity.userEntity;
        QTrackingEntity trackingEntity = QTrackingEntity.trackingEntity;
        QBidEntity bidEntity = QBidEntity.bidEntity;

        Predicate whereClause = filters == null ? null
                : QPredicate.builder()
                .add(filters.getSellerId(), lot.seller.id::eq)
                .add(filters.getTrackingUserId(), trackingEntity.user.id::eq)
                .add(filters.getBidUserId(), bidEntity.user.id::eq)
                .buildAnd();

        OrderSpecifier<?>[] orderClause = order == null ? new OrderSpecifier<?>[]{}
                : QOrder.builder()
                .add(order.getLotId(), lot.id)
                .build();

        Long totalCount = new JPAQuery<>(entityManager)
                .select(lot.id.count())
                .from(lot)
                .leftJoin(lot.tracking, trackingEntity)
                .leftJoin(lot.bids, bidEntity)
                .where(whereClause)
                .fetchOne();

        var filteredLotsIds = new JPAQuery<>(entityManager)
                .select(lot.id)
                .from(lot)
                .leftJoin(lot.tracking, trackingEntity)
                .leftJoin(lot.bids, bidEntity)
                .where(whereClause)
                .restrict(new QueryModifiers((long) limit, (long) (page - 1) * limit))
                .fetch();

        List<LotEntity> content = new JPAQuery<>(entityManager)
                .select(lot)
                .from(lot)
                .innerJoin(lot.images, lotImage).fetchJoin()
                .innerJoin(lot.seller, seller).fetchJoin()
                .leftJoin(lot.tracking, trackingEntity)
                .where(lot.id.in(filteredLotsIds))
                .orderBy(orderClause)
                .fetch();

        return new PageImpl<>(content, PageRequest.of(page - 1, limit), totalCount == null ? 0 : totalCount);
    }

    @Override
    public Optional<LotEntity> findFullLotInfo(Long lotId) {
        QLotEntity lot = QLotEntity.lotEntity;
        QLotImageEntity lotImage = QLotImageEntity.lotImageEntity;
        QUserEntity seller = QUserEntity.userEntity;
        QTrackingEntity tracking = QTrackingEntity.trackingEntity;

        return Optional.ofNullable(new JPAQuery<>(entityManager)
                .select(lot)
                .from(lot)
                .innerJoin(lot.images, lotImage).fetchJoin()
                .innerJoin(lot.seller, seller).fetchJoin()
                .leftJoin(lot.tracking, tracking).fetchJoin()
                .where(lot.id.eq(lotId))
                .fetchOne());
    }
}


