package com.example.notification_service.Controller;


import com.example.notification_service.Entity.Notification;
import com.example.notification_service.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {

        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @PutMapping("/{id}/mark-as-read")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long id) {
        notificationService.markNotificationAsRead(id);
        return ResponseEntity.ok("Notification marked as read!");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted!");
    }
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUserNotifications(@PathVariable Long userId) {
        notificationService.deleteAllNotificationsByUserId(userId);
        return ResponseEntity.ok("All notifications deleted for user: " + userId);
    }







}
