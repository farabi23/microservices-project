package com.example.notification_service.Service;

import com.example.notification_service.DTO.NotificationDTO;
import com.example.notification_service.Entity.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KafkaConsumerService {

    @Autowired
    private NotificationService notificationSaveService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void listen(String rawMessage) {
        try {
            NotificationDTO notificationDTO = objectMapper.readValue(rawMessage, NotificationDTO.class);

            Notification notification = new Notification();
            notification.setUserId(notificationDTO.getUserId());
            notification.setUsername(notificationDTO.getUsername());
            notification.setMessage(notificationDTO.getMessage());
            notification.setRead(false);
            notification.setTimestamp(java.time.LocalDateTime.now());

            notificationSaveService.saveNotification(notification);
            System.out.println("Notification received");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
