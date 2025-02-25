package com.example.notification_service.DTO;

public class NotificationDTO {
    private Long userId;
    private String message;

    public NotificationDTO(Long userId, String message) {
        this.userId = userId;
        this.message = message;
    }
    public NotificationDTO() {}

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
