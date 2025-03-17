package com.example.notification_service.Service;

import com.example.notification_service.Entity.Notification;
import com.example.notification_service.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);

    }

    public List<Notification> getNotificationsByUserName(String userName) {
        return notificationRepository.findByUsername(userName);
    }

    public Notification getNotificationById(Long id) {

        return notificationRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Notification not found"));
    }

    public void markNotificationAsRead(Long id) {
        Notification notification = getNotificationById(id);
        assert notification != null;
        notification.setRead(true);
        notificationRepository.save(notification);

    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    public void deleteAllNotificationsByUserId(Long userId) {
        notificationRepository.deleteByUserId(userId);


    }
    public void deleteAllNotificationsByUserName(String userName) {
        notificationRepository.deleteAllByUsername(userName);
    }
}
