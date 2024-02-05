package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transactions", schema = "auction", catalog = "auction")
public class TransactionEntity {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime dealTime;
    private LotEntity lotEntity;
    private UserEntity sellerEntity;
    private UserEntity buyerEntity;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "deal_time")
    public LocalDateTime getDealTime() {
        return dealTime;
    }

    public void setDealTime(LocalDateTime dealTime) {
        this.dealTime = dealTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntity that = (TransactionEntity) o;
        return id == that.id && Objects.equals(amount, that.amount) && Objects.equals(dealTime, that.dealTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, dealTime);
    }

    @ManyToOne
    @JoinColumn(name = "lot_id", referencedColumnName = "id", nullable = false)
    public LotEntity getLotEntity() {
        return lotEntity;
    }

    public void setLotEntity(LotEntity lotsByLotId) {
        this.lotEntity = lotsByLotId;
    }

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false)
    public UserEntity getSellerEntity() {
        return sellerEntity;
    }

    public void setSellerEntity(UserEntity usersBySellerId) {
        this.sellerEntity = usersBySellerId;
    }

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id", nullable = false)
    public UserEntity getBuyerEntity() {
        return buyerEntity;
    }

    public void setBuyerEntity(UserEntity usersByBuyerId) {
        this.buyerEntity = usersByBuyerId;
    }
}
