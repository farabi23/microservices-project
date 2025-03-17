package com.example.notification_service.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")  //
    private String username;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "is_read")
    private boolean isRead;

    public Notification() {}

    public Notification(Long id, Long userId, String username, String message,  LocalDateTime timestamp, boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.message = message;

        this.timestamp = timestamp;
        this.isRead = isRead;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
