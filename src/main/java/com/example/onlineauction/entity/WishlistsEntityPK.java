package com.example.onlineauction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class WishlistsEntityPK implements Serializable {
    private long userId;
    private long goodId;

    @Column(name = "user_id")
    @Id
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "good_id")
    @Id
    public long getGoodId() {
        return goodId;
    }

    public void setGoodId(long goodId) {
        this.goodId = goodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishlistsEntityPK that = (WishlistsEntityPK) o;
        return userId == that.userId && goodId == that.goodId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, goodId);
    }
}
