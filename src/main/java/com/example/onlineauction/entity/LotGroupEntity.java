package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "lots_groups", schema = "auction", catalog = "auction")
public class LotGroupEntity {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdTime;
    private List<LotEntity> lotsById;
    private UserEntity usersByCreatorId;

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

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "created_time")
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotGroupEntity that = (LotGroupEntity) o;
        return id.equals(that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, createdTime);
    }

    @OneToMany(mappedBy = "lotGroup")
    public List<LotEntity> getLotsById() {
        return lotsById;
    }

    public void setLotsById(List<LotEntity> lotsById) {
        this.lotsById = lotsById;
    }

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUsersByCreatorId() {
        return usersByCreatorId;
    }

    public void setUsersByCreatorId(UserEntity usersByCreatorId) {
        this.usersByCreatorId = usersByCreatorId;
    }
}
