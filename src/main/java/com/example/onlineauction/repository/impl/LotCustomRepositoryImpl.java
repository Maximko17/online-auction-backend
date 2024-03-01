package com.example.onlineauction.repository.impl;

import com.example.onlineauction.dto.lot.LotImageDto;
import com.example.onlineauction.dto.lot.LotInfoDto;
import com.example.onlineauction.dto.lot.LotListFiltersDto;
import com.example.onlineauction.dto.lot.LotListOrderDto;
import com.example.onlineauction.dto.user.UserInfoDto;
import com.example.onlineauction.entity.*;
import com.example.onlineauction.repository.LotCustomRepository;
import com.example.onlineauction.security.AuthenticationFacade;
import com.example.onlineauction.security.JwtUserDetails;
import com.querydsl.core.QueryModifiers;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class LotCustomRepositoryImpl implements LotCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    private final AuthenticationFacade authenticationFacade;

    public Page<LotInfoDto> findByFilters(LotListFiltersDto filters, LotListOrderDto order, Integer page, Integer limit) {
        QLotEntity lot = QLotEntity.lotEntity;
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

        List<Long> filteredLotIds = new JPAQuery<>(entityManager)
                .select(lot.id)
                .from(lot)
                .leftJoin(lot.tracking, trackingEntity)
                .leftJoin(lot.bids, bidEntity)
                .where(whereClause)
                .restrict(new QueryModifiers((long) limit, (long) (page - 1) * limit))
                .fetch();

        List<LotInfoDto> lotInfoDtos = new JPAQuery<>(entityManager)
                .select(getSelectProjection(lot, trackingEntity, seller))
                .from(lot)
                .innerJoin(lot.seller, seller)
                .where(lot.id.in(filteredLotIds))
                .orderBy(orderClause)
                .fetch();

        List<Long> lotIds = lotInfoDtos.stream().map(LotInfoDto::getId).collect(Collectors.toList());
        Map<Long, List<String>> imagesByLotId = findImagesForLots(lotIds);

        for (LotInfoDto lotInfoDto : lotInfoDtos) {
            List<String> photoUrls = imagesByLotId.getOrDefault(lotInfoDto.getId(), Collections.emptyList());
            lotInfoDto.setImages(photoUrls);
        }

        return new PageImpl<>(lotInfoDtos, PageRequest.of(page - 1, limit), totalCount == null ? 0 : totalCount);
    }

    private Map<Long, List<String>> findImagesForLots(List<Long> lotIds) {
        QLotImageEntity lotImage = QLotImageEntity.lotImageEntity;
        List<LotImageDto> imagesForLots = new JPAQuery<Void>(entityManager)
                .select(Projections.constructor(
                        LotImageDto.class,
                        lotImage.id,
                        lotImage.lot.id,
                        lotImage.image
                ))
                .from(lotImage)
                .where(lotImage.lot.id.in(lotIds))
                .fetch();

        return imagesForLots.stream()
                .collect(Collectors.groupingBy(LotImageDto::getLotId,
                        Collectors.mapping(LotImageDto::getImage, Collectors.toList())));
    }

    private ConstructorExpression<LotInfoDto> getSelectProjection(QLotEntity lot,
                                                                  QTrackingEntity tracking,
                                                                  QUserEntity seller) {
        Authentication authentication = authenticationFacade.getAuthentication();

        BooleanExpression isTrackingQuery = authentication != null
                ? JPAExpressions.selectOne()
                .from(tracking)
                .where(tracking.user.id.eq(((JwtUserDetails) authentication.getPrincipal()).getUserEntity().getId())
                        .and(tracking.lot.id.eq(lot.id)))
                .exists()
                : Expressions.asBoolean(false);

        return Projections.constructor(
                LotInfoDto.class,
                lot.id,
                lot.name,
                lot.description,
                lot.bidIncrement,
                lot.startBid,
                lot.lastBid,
                lot.totalBids,
                lot.startTime,
                lot.endTime,
                lot.status,
                Projections.constructor(
                        UserInfoDto.class,
                        seller.id,
                        seller.username,
                        seller.email,
                        seller.role,
                        seller.rating,
                        seller.image
                ),
                isTrackingQuery
        );
    }

    @Override
    public Optional<LotInfoDto> findFullLotInfo(Long lotId) {
        QLotEntity lot = QLotEntity.lotEntity;
        QUserEntity seller = QUserEntity.userEntity;
        QTrackingEntity tracking = QTrackingEntity.trackingEntity;

        Optional<LotInfoDto> lotInfoDto = Optional.ofNullable(
                new JPAQuery<>(entityManager)
                        .select(getSelectProjection(lot, tracking, seller))
                        .from(lot)
                        .innerJoin(lot.seller, seller)
                        .leftJoin(lot.tracking, tracking)
                        .where(lot.id.eq(lotId))
                        .fetchOne()
        );

        lotInfoDto.ifPresent(lotInfo -> {
            Map<Long, List<String>> imagesForLots = findImagesForLots(List.of(lotInfo.getId()));
            lotInfo.setImages(imagesForLots.get(lotInfo.getId()));
        });

        return lotInfoDto;
    }
}


