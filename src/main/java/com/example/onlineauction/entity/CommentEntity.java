package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "comments", schema = "auction", catalog = "auction")
public class CommentEntity {
    private Long id;
    private String comment;
    private String sendTime;
    private CommentEntity parentComment;

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
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "send_time")
    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return id == that.id && Objects.equals(comment, that.comment) && Objects.equals(sendTime, that.sendTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment, sendTime);
    }

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    public CommentEntity getParentComment() {
        return parentComment;
    }

    public void setParentComment(CommentEntity commentsByParentId) {
        this.parentComment = commentsByParentId;
    }
}
