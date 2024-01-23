package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bids", schema = "auction", catalog = "auction")
public class BidEntity {
    private Long id;
    private BigDecimal bid;
    private LocalDateTime bidTime;
    private AuctionEntity auctionEntity;
    private UserEntity userEntity;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "bid")
    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    @Basic
    @Column(name = "bid_time")
    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BidEntity that = (BidEntity) o;
        return id == that.id && Objects.equals(bid, that.bid) && Objects.equals(bidTime, that.bidTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bid, bidTime);
    }

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "id", nullable = false)
    public AuctionEntity getAuctionEntity() {
        return auctionEntity;
    }

    public void setAuctionEntity(AuctionEntity auctionsByAuctionId) {
        this.auctionEntity = auctionsByAuctionId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity usersByUserId) {
        this.userEntity = usersByUserId;
    }
}
