package com.example.onlineauction.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "tracking", schema = "auction", catalog = "auction")
public class TrackingEntity {
    private TrackingEntityPK trackingEntityPK;
    private UserEntity user;
    private LotEntity lot;

    @EmbeddedId
    public TrackingEntityPK getTrackingEntityPK() {
        return trackingEntityPK;
    }

    public void setTrackingEntityPK(TrackingEntityPK trackingEntityPK) {
        this.trackingEntityPK = trackingEntityPK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingEntity that = (TrackingEntity) o;
        return trackingEntityPK.equals(that.trackingEntityPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingEntityPK);
    }

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity usersByUserId) {
        this.user = usersByUserId;
    }

    @ManyToOne
    @MapsId("lotId")
    @JoinColumn(name = "lot_id", referencedColumnName = "id")
    public LotEntity getLot() {
        return lot;
    }

    public void setLot(LotEntity lotsByLotId) {
        this.lot = lotsByLotId;
    }
}
