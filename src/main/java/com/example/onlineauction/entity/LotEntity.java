package com.example.onlineauction.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "lots", schema = "auction", catalog = "auction")
public class LotEntity {
    private Long id;
    private String name;
    private String description;
    private BigDecimal bidIncrement;
    private BigDecimal startBid;
    private BigDecimal lastBid;
    private Integer totalBids;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Status status;
    private LotGroupEntity lotGroup;
    private List<LotImageEntity> images;
    private UserEntity seller;
    private CategoryEntity category;
    private List<TrackingEntity> tracking;
    private List<BidEntity> bids;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "start_bid")
    public BigDecimal getStartBid() {
        return startBid;
    }

    public void setStartBid(BigDecimal startBid) {
        this.startBid = startBid;
    }

    @Basic
    @Column(name = "last_bid")
    public BigDecimal getLastBid() {
        return lastBid;
    }

    public void setLastBid(BigDecimal lastBid) {
        this.lastBid = lastBid;
    }

    @Basic
    @Column(name = "total_bids")
    public Integer getTotalBids() {
        return totalBids;
    }

    public void setTotalBids(Integer totalBids) {
        this.totalBids = totalBids;
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
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write = "?::status")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotEntity that = (LotEntity) o;
        return id.equals(that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(bidIncrement, that.bidIncrement) && Objects.equals(startBid, that.startBid) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, bidIncrement, startBid, startTime, endTime, status);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    public LotGroupEntity getLotGroup() {
        return lotGroup;
    }

    public void setLotGroup(LotGroupEntity lotsGroupsByGroupId) {
        this.lotGroup = lotsGroupsByGroupId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false)
    public UserEntity getSeller() {
        return seller;
    }

    public void setSeller(UserEntity sellerEntity) {
        this.seller = sellerEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<LotImageEntity> getImages() {
        return images;
    }

    public void setImages(List<LotImageEntity> lotImages) {
        this.images = lotImages;
    }

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<TrackingEntity> getTracking() {
        return tracking;
    }

    public void setTracking(List<TrackingEntity> tracking) {
        this.tracking = tracking;
    }

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<BidEntity> getBids() {
        return bids;
    }

    public void setBids(List<BidEntity> bids) {
        this.bids = bids;
    }
}
