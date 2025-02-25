package com.example.notification_service.Service;

import com.example.notification_service.DTO.NotificationDTO;
import com.example.notification_service.Entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KafkaConsumerService {

    @Autowired
    private NotificationSaveService notificationSaveService;

    @KafkaListener(topics="user-events", groupId = "notification-group")
    public void listen(NotificationDTO notificationDTO) {

        Notification notification = new Notification();
        notification.setUserId(notificationDTO.getUserId());
        notification.setMessage(notificationDTO.getMessage());
        notification.setRead(false);
        notification.setTimestamp(LocalDateTime.now());
        //System.out.println("NotificationService received: "+ message);
        notificationSaveService.saveNotification(notification);

        // send email, push notification, etc.
    }


}
