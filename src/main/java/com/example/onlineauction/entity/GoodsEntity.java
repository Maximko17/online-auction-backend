package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "goods", schema = "auction", catalog = "auction")
public class GoodsEntity {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createTime;
    private AuctionsEntity auctionEntity;
    private GoodsCategoriesEntity goodsCategorieEntity;
    private UsersEntity usersBySellerId;
    private List<String> images;
    private List<WishlistsEntity> wishlistsEntities;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    @Column(name = "create_time")
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsEntity that = (GoodsEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, createTime);
    }

    @OneToOne(mappedBy = "goodEntity")
    public AuctionsEntity getAuctionEntity() {
        return auctionEntity;
    }

    public void setAuctionEntity(AuctionsEntity auctionsById) {
        this.auctionEntity = auctionsById;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    public GoodsCategoriesEntity getGoodsCategorieEntity() {
        return goodsCategorieEntity;
    }

    public void setGoodsCategorieEntity(GoodsCategoriesEntity goodsCategoriesByCategoryId) {
        this.goodsCategorieEntity = goodsCategoriesByCategoryId;
    }

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    public UsersEntity getUsersBySellerId() {
        return usersBySellerId;
    }

    public void setUsersBySellerId(UsersEntity usersBySellerId) {
        this.usersBySellerId = usersBySellerId;
    }

    @Column(name = "image")
    @CollectionTable(name = "goods_images")
    @ElementCollection(fetch = FetchType.EAGER)
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @OneToMany(mappedBy = "goodEntity")
    public List<WishlistsEntity> getWishlistsEntities() {
        return wishlistsEntities;
    }

    public void setWishlistsEntities(List<WishlistsEntity> wishlistsById) {
        this.wishlistsEntities = wishlistsById;
    }
}
