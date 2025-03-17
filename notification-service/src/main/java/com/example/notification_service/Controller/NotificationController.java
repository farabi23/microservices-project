package com.example.notification_service.Controller;


import com.example.notification_service.Entity.Notification;
import com.example.notification_service.Service.NotificationService;
import com.example.notification_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user")
    public ResponseEntity<List<Notification>> getUserNotifications(
            @RequestHeader("Authorization") String token) {

        String username = jwtUtil.extractUsername(token.substring(7));

        return ResponseEntity.ok(notificationService.getNotificationsByUserName(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id,
                                                            @RequestHeader("Authorization") String token) {

        String username = jwtUtil.extractUsername(token.substring(7));

        Notification notification = notificationService.getNotificationById(id);

        if(!notification.getUsername().equals(username)) {
            return ResponseEntity.status(403).build();

        }

        return ResponseEntity.ok(notification);

    }

    @PutMapping("/{id}/mark-as-read")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long id,
                                                         @RequestHeader("Authorization") String token) {

        String username = jwtUtil.extractUsername(token.substring(7));
        Notification notification = notificationService.getNotificationById(id);

        if(!notification.getUsername().equals(username)) {
            return ResponseEntity.status(403).body("Unauthorized");
        }


        notificationService.markNotificationAsRead(id);
        return ResponseEntity.ok("Notification marked as read!");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id,
                                                     @RequestHeader("Authorization") String token) {

        String username = jwtUtil.extractUsername(token.substring(7));
        Notification notification = notificationService.getNotificationById(id);

        if(!notification.getUsername().equals(username)) {
            return ResponseEntity.status(403).body("Unauthorized");
        }

        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted!");
    }
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUserNotifications(@RequestHeader("Authorization") String token) {

        String username = jwtUtil.extractUsername(token.substring(7));

        notificationService.deleteAllNotificationsByUserName(username);
        return ResponseEntity.ok("All notifications deleted for user: " + username);
    }







}
