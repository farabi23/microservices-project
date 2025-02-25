package com.example.notification_service.Service;

import com.example.notification_service.Entity.Notification;
import com.example.notification_service.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByUserId(Long userId) {

        List<Notification> notificationList =
                notificationRepository.findAllById(userId);
        return notificationList;

    }

    public Notification getNotificationById(Long notificationId) {

        return notificationRepository.findById(notificationId).orElse(null);
    }

    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        assert notification != null;
        notification.setRead(true);
        notificationRepository.save(notification);

    }

    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    public void deleteAllNotificationsByUserId(Long userId) {
        List<Notification> notificationList = notificationRepository.findAllById(userId);
        notificationRepository.deleteAll(notificationList);

    }
}
