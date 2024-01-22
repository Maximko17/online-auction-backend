package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "auction", catalog = "auction")
public class UsersEntity {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<AuctionsEntity> auctionEntities;
    private List<BidsEntity> bidEntities;
    private List<GoodsEntity> goodEntities;
    private List<UsersRolesEntity> usersRolesEntities;
    private List<WishlistsEntity> wishlistsEntities;

    @Id
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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }

    @OneToMany(mappedBy = "userEntity")
    public List<AuctionsEntity> getAuctionEntities() {
        return auctionEntities;
    }

    public void setAuctionEntities(List<AuctionsEntity> auctionsById) {
        this.auctionEntities = auctionsById;
    }

    @OneToMany(mappedBy = "userEntity")
    public List<BidsEntity> getBidEntities() {
        return bidEntities;
    }

    public void setBidEntities(List<BidsEntity> bidsById) {
        this.bidEntities = bidsById;
    }

    @OneToMany(mappedBy = "usersBySellerId")
    public List<GoodsEntity> getGoodEntities() {
        return goodEntities;
    }

    public void setGoodEntities(List<GoodsEntity> goodsById) {
        this.goodEntities = goodsById;
    }

    @OneToMany(mappedBy = "userEntity")
    public List<UsersRolesEntity> getUsersRolesEntities() {
        return usersRolesEntities;
    }

    public void setUsersRolesEntities(List<UsersRolesEntity> usersRolesById) {
        this.usersRolesEntities = usersRolesById;
    }

    @OneToMany(mappedBy = "userEntity")
    public List<WishlistsEntity> getWishlistsEntities() {
        return wishlistsEntities;
    }

    public void setWishlistsEntities(List<WishlistsEntity> wishlistsById) {
        this.wishlistsEntities = wishlistsById;
    }
}
