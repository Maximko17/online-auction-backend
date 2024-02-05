package com.example.onlineauction.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "messages", schema = "auction", catalog = "auction")
public class MessageEntity {
    private Long id;
    private String message;
    private LocalDateTime sendTime;
    private LotEntity lotsByLotId;
    private UserEntity usersBySenderId;
    private UserEntity usersByRecipientId;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "send_time")
    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        return id == that.id && Objects.equals(message, that.message) && Objects.equals(sendTime, that.sendTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, sendTime);
    }

    @ManyToOne
    @JoinColumn(name = "lot_id", referencedColumnName = "id", nullable = false)
    public LotEntity getLotsByLotId() {
        return lotsByLotId;
    }

    public void setLotsByLotId(LotEntity lotsByLotId) {
        this.lotsByLotId = lotsByLotId;
    }

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUsersBySenderId() {
        return usersBySenderId;
    }

    public void setUsersBySenderId(UserEntity usersBySenderId) {
        this.usersBySenderId = usersBySenderId;
    }

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUsersByRecipientId() {
        return usersByRecipientId;
    }

    public void setUsersByRecipientId(UserEntity usersByRecipientId) {
        this.usersByRecipientId = usersByRecipientId;
    }
}
