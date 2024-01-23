package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users", schema = "auction", catalog = "auction")
public class UserEntity {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<AuctionEntity> auctionEntities;
    private List<BidEntity> bidEntities;
    private List<GoodEntity> goodEntities;
    private Set<Role> roles;
    private List<WishlistEntity> wishlistsEntities;
    private String passwordConfirmation;

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

    @Transient
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }

    @OneToMany(mappedBy = "userEntity")
    public List<AuctionEntity> getAuctionEntities() {
        return auctionEntities;
    }

    public void setAuctionEntities(List<AuctionEntity> auctionsById) {
        this.auctionEntities = auctionsById;
    }

    @OneToMany(mappedBy = "userEntity")
    public List<BidEntity> getBidEntities() {
        return bidEntities;
    }

    public void setBidEntities(List<BidEntity> bidsById) {
        this.bidEntities = bidsById;
    }

    @OneToMany(mappedBy = "usersBySellerId")
    public List<GoodEntity> getGoodEntities() {
        return goodEntities;
    }

    public void setGoodEntities(List<GoodEntity> goodsById) {
        this.goodEntities = goodsById;
    }

    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(value = EnumType.STRING)
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> usersRoles) {
        this.roles = usersRoles;
    }

    @OneToMany(mappedBy = "userEntity")
    public List<WishlistEntity> getWishlistsEntities() {
        return wishlistsEntities;
    }

    public void setWishlistsEntities(List<WishlistEntity> wishlistsById) {
        this.wishlistsEntities = wishlistsById;
    }

}
