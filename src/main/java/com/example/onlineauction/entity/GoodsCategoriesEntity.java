package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "goods_categories", schema = "auction", catalog = "auction")
public class GoodsCategoriesEntity {
    private Long id;
    private String name;
    private List<GoodEntity> goodsEntities;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsCategoriesEntity that = (GoodsCategoriesEntity) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "goodsCategorieEntity")
    public List<GoodEntity> getGoodsEntities() {
        return goodsEntities;
    }

    public void setGoodsEntities(List<GoodEntity> goodsEntities) {
        this.goodsEntities = goodsEntities;
    }
}
