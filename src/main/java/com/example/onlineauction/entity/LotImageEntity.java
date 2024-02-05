package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "lots_images", schema = "auction", catalog = "auction")
public class LotImageEntity {
    private Long id;
    private String image;
    private LotEntity lotsByLotId;

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
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotImageEntity that = (LotImageEntity) o;
        return id == that.id && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image);
    }

    @ManyToOne
    @JoinColumn(name = "lot_id", referencedColumnName = "id", nullable = false)
    public LotEntity getLotsByLotId() {
        return lotsByLotId;
    }

    public void setLotsByLotId(LotEntity lotsByLotId) {
        this.lotsByLotId = lotsByLotId;
    }
}
