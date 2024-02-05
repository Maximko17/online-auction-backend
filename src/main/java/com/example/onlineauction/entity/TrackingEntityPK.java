package com.example.onlineauction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class TrackingEntityPK implements Serializable {
    private Long userId;
    private Long lotId;

    @Column(name = "user_id")
    @Id
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "lot_id")
    @Id
    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingEntityPK that = (TrackingEntityPK) o;
        return userId == that.userId && lotId == that.lotId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, lotId);
    }
}
