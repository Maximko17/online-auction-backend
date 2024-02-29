package com.example.onlineauction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categories", schema = "auction", catalog = "auction")
public class CategoryEntity {
    private Long id;
    private String name;
    private CategoryEntity parentCategory;
//    private List<CategoryEntity> categoryEntities;

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
        CategoryEntity that = (CategoryEntity) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    public CategoryEntity getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(CategoryEntity categoriesByParentId) {
        this.parentCategory = categoriesByParentId;
    }

//    @OneToMany(mappedBy = "parentCategory")
//    public List<CategoryEntity> getCategoryEntities() {
//        return categoryEntities;
//    }
//
//    public void setCategoryEntities(List<CategoryEntity> categoriesById) {
//        this.categoryEntities = categoriesById;
//    }

}
