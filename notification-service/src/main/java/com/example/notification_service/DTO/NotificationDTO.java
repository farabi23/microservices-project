package com.example.notification_service.DTO;

public class NotificationDTO {
    private Long userId;
    private String message;
    private String username;

    public NotificationDTO(Long userId, String message, String username) {

        this.userId = userId;
        this.username = username;
        this.message = message;

    }
    public NotificationDTO() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
