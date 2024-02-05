package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tracking", schema = "auction", catalog = "auction")
@IdClass(TrackingEntityPK.class)
public class TrackingEntity {
    private Long userId;
    private Long lotId;
    private UserEntity userEntity;
    private LotEntity lotEntity;

    @Id
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "lot_id")
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
        TrackingEntity that = (TrackingEntity) o;
        return userId == that.userId && lotId == that.lotId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, lotId);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity usersByUserId) {
        this.userEntity = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "lot_id", referencedColumnName = "id", nullable = false)
    public LotEntity getLotEntity() {
        return lotEntity;
    }

    public void setLotEntity(LotEntity lotsByLotId) {
        this.lotEntity = lotsByLotId;
    }
}
