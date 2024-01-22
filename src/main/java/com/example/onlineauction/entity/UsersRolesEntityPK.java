package com.example.onlineauction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class UsersRolesEntityPK implements Serializable {
    private Long userId;
    private String role;

    @Column(name = "user_id")
    @Id
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "role")
    @Id
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
        UsersRolesEntityPK that = (UsersRolesEntityPK) o;
        return userId == that.userId && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, role);
    }
}
