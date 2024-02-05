package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "lots_categories", schema = "auction", catalog = "auction")
@IdClass(LotCategoryEntityPK.class)
public class LotCategoryEntity {
    private Long categoryId;
    private Long lotId;
    private CategoryEntity categoriesByCategoryId;
    private LotEntity lotsByLotId;

    @Id
    @Column(name = "category_id")
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
        LotCategoryEntity that = (LotCategoryEntity) o;
        return categoryId == that.categoryId && lotId == that.lotId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, lotId);
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    public CategoryEntity getCategoriesByCategoryId() {
        return categoriesByCategoryId;
    }

    public void setCategoriesByCategoryId(CategoryEntity categoriesByCategoryId) {
        this.categoriesByCategoryId = categoriesByCategoryId;
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
