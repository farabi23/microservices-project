package com.example.notification_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private NotificationSaveService notificationSaveService;

    @KafkaListener(topics="user-events", groupId = "notification-group")
    public void listen(String message) {
        System.out.println("NotificationService received: "+ message);
        notificationSaveService.saveNotification();

        // send email, push notification, etc.
    }


}
