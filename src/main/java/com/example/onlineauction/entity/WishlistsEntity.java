package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "wishlists", schema = "auction", catalog = "auction")
@IdClass(WishlistsEntityPK.class)
public class WishlistsEntity {
    private Long userId;
    private Long goodId;
    private UsersEntity userEntity;
    private GoodsEntity goodEntity;

    @Id
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "good_id")
    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishlistsEntity that = (WishlistsEntity) o;
        return userId == that.userId && goodId == that.goodId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, goodId);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UsersEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UsersEntity usersByUserId) {
        this.userEntity = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "good_id", referencedColumnName = "id", nullable = false)
    public GoodsEntity getGoodEntity() {
        return goodEntity;
    }

    public void setGoodEntity(GoodsEntity goodsByGoodId) {
        this.goodEntity = goodsByGoodId;
    }
}
