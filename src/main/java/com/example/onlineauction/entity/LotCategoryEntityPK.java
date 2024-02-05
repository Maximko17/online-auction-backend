package com.example.onlineauction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class LotCategoryEntityPK implements Serializable {
    private Long categoryId;
    private Long lotId;

    @Column(name = "category_id")
    @Id
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
        LotCategoryEntityPK that = (LotCategoryEntityPK) o;
        return categoryId == that.categoryId && lotId == that.lotId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, lotId);
    }
}
