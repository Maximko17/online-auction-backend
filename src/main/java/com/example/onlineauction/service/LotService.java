package com.example.onlineauction.service;

import com.example.onlineauction.dto.lot.LotFullInfoDto;
import com.example.onlineauction.dto.lot.LotListFiltersDto;
import com.example.onlineauction.dto.lot.LotListOrderDto;
import com.example.onlineauction.dto.lot.editLot.EditLotRequestData;
import com.example.onlineauction.entity.*;
import com.example.onlineauction.exception.AccessDeniedException;
import com.example.onlineauction.exception.ConflictException;
import com.example.onlineauction.exception.ResourceNotFoundException;
import com.example.onlineauction.repository.LotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LotService {

    private static final Logger logger = LoggerFactory.getLogger(LotService.class);
    private final LotRepository lotRepository;
    private final LotImageService lotImageService;
    private final BidService bidService;
    private final TransactionService transactionService;

    public Page<LotFullInfoDto> getListByFilters(LotListFiltersDto filters, LotListOrderDto order, Integer page, Integer limit) {
        return lotRepository.findByFilters(filters, order, page, limit);
    }

    @Transactional
    public Long create(LotEntity lot, List<MultipartFile> images, UserEntity authUser) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("TEST");
        lot.setSeller(authUser);
        lot.setStatus(Status.REVIEW);
        lot.setCategory(categoryEntity);
        lot.setImages(lotImageService.uploadNew(images, lot));
        LotEntity savedLot = lotRepository.save(lot);

        return savedLot.getId();
    }

    @Transactional
    public void edit(Long id, EditLotRequestData editReqData, List<MultipartFile> images, UserEntity authUser) {
        LotEntity lot = getById(id);
        if (!authUser.getId().equals(lot.getSeller().getId())) {
            throw new AccessDeniedException();
        }
        if (lot.getStatus() != Status.REVIEW && lot.getStatus() != Status.NEW) {
            throw new ConflictException("Запрещено вносить правки в лот со статусом " + lot.getStatus().name());
        }

        lot.setStatus(Status.REVIEW);
        lot.setName(editReqData.getName());
        lot.setDescription(editReqData.getDescription());
        lot.setBidIncrement(editReqData.getBidIncrement());
        lot.setStartBid(editReqData.getStartBid());
        lot.setStartTime(editReqData.getStartTime());
        lot.setEndTime(editReqData.getEndTime());
        lot.setCategory(lot.getCategory());
        if (CollectionUtils.isNotEmpty(images)) {
            lot.setImages(lotImageService.update(images, lot));
        }
        lotRepository.save(lot);
    }

    @Transactional
    public LotFullInfoDto getFullLotInfo(Long id) {
        LotFullInfoDto lotFullInfoDto = lotRepository.findFullLotInfo(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lot not found."));

        lotFullInfoDto.setStatus(updateStatusIfExpire(
                lotFullInfoDto.getId(),
                lotFullInfoDto.getStartTime(),
                lotFullInfoDto.getEndTime(),
                lotFullInfoDto.getStatus()
        ));
        return lotFullInfoDto;
    }

    @Transactional
    public LotEntity getById(Long id) {
        LotEntity lotEntity = lotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lot not found."));

        lotEntity.setStatus(updateStatusIfExpire(
                lotEntity.getId(),
                lotEntity.getStartTime(),
                lotEntity.getEndTime(),
                lotEntity.getStatus()
        ));
        return lotEntity;
    }

    public LotEntity update(LotEntity lotEntity) {
        if (lotEntity == null) {
            return null;
        }
        return lotRepository.save(lotEntity);
    }

    public boolean isClosedById(Long lotId) {
        LotEntity lot = this.getById(lotId);
        return lot.getStatus().equals(Status.CLOSED);
    }

    private Status updateStatusIfExpire(Long id, LocalDateTime startTime, LocalDateTime endTime, Status lotStatus) {
        if (LocalDateTime.now().isAfter(startTime)
                && LocalDateTime.now().isBefore(endTime)
                && !lotStatus.equals(Status.ACTIVE)) {
            lotRepository.markAsActive(id);
            return Status.ACTIVE;
        } else if (LocalDateTime.now().isAfter(endTime)
                && (!lotStatus.equals(Status.CLOSED) && !lotStatus.equals(Status.SOLD))) {
            return closeLotAuction(id);
        }
        return lotStatus;
    }

    private Status closeLotAuction(Long id) {
        Optional<BidEntity> lastBid = bidService.findLastByLotId(id);
        LotEntity lot = lotRepository.findById(id).get();

        Status finalStatus = Status.CLOSED;
        if (lastBid.isPresent()) {
            BidEntity bid = lastBid.get();
            TransactionEntity transaction = new TransactionEntity();
            transaction.setAmount(bid.getBid());
            transaction.setBuyerEntity(bid.getUser());
            transaction.setSellerEntity(lot.getSeller());
            transaction.setDealTime(bid.getBidTime());
            transaction.setLotEntity(lot);
            transactionService.save(transaction);
            finalStatus = Status.SOLD;
        }
        lot.setStatus(finalStatus);
        lotRepository.save(lot);

        return finalStatus;
    }

}
