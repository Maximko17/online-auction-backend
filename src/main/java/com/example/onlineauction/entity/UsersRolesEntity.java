package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users_roles", schema = "auction", catalog = "auction")
@IdClass(UsersRolesEntityPK.class)
public class UsersRolesEntity {
    private Long userId;
    private String role;
    private UsersEntity userEntity;

    @Id
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersRolesEntity that = (UsersRolesEntity) o;
        return userId == that.userId && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, role);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UsersEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UsersEntity usersByUserId) {
        this.userEntity = usersByUserId;
    }
}
