package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "auctions", schema = "auction", catalog = "auction")
public class AuctionEntity {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal startBid;
    private BigDecimal currentBid;
    private BigDecimal bidIncrement;
    private BigDecimal finalBid;
    private Object status;
    private Integer bidCount;
    private GoodEntity goodEntity;
    private UserEntity userEntity;
    private List<BidEntity> bidsEntities;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "start_time")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "start_bid")
    public BigDecimal getStartBid() {
        return startBid;
    }

    public void setStartBid(BigDecimal startBid) {
        this.startBid = startBid;
    }

    @Basic
    @Column(name = "current_bid")
    public BigDecimal getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(BigDecimal currentBid) {
        this.currentBid = currentBid;
    }

    @Basic
    @Column(name = "bid_increment")
    public BigDecimal getBidIncrement() {
        return bidIncrement;
    }

    public void setBidIncrement(BigDecimal bidIncrement) {
        this.bidIncrement = bidIncrement;
    }

    @Basic
    @Column(name = "final_bid")
    public BigDecimal getFinalBid() {
        return finalBid;
    }

    public void setFinalBid(BigDecimal finalBid) {
        this.finalBid = finalBid;
    }

    @Basic
    @Column(name = "status")
    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    @Basic
    @Column(name = "bid_count")
    public Integer getBidCount() {
        return bidCount;
    }

    public void setBidCount(Integer bidCount) {
        this.bidCount = bidCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuctionEntity that = (AuctionEntity) o;
        return id == that.id && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(startBid, that.startBid) && Objects.equals(currentBid, that.currentBid) && Objects.equals(bidIncrement, that.bidIncrement) && Objects.equals(finalBid, that.finalBid) && Objects.equals(status, that.status) && Objects.equals(bidCount, that.bidCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime, startBid, currentBid, bidIncrement, finalBid, status, bidCount);
    }

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    public GoodEntity getGoodEntity() {
        return goodEntity;
    }

    public void setGoodEntity(GoodEntity goodsById) {
        this.goodEntity = goodsById;
    }

    @ManyToOne
    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity usersByWinnerId) {
        this.userEntity = usersByWinnerId;
    }

    @OneToMany(mappedBy = "auctionEntity")
    public List<BidEntity> getBidsEntities() {
        return bidsEntities;
    }

    public void setBidsEntities(List<BidEntity> bidsEntities) {
        this.bidsEntities = bidsEntities;
    }
}
