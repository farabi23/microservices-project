package com.example.notification_service.Service;

import com.example.notification_service.Entity.Notification;
import com.example.notification_service.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationSaveService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }
}
