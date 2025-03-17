package com.example.notification_service.Repository;

import com.example.notification_service.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface NotificationRepository extends JpaRepository<Notification, Long> {


    List<Notification> findAllById(Long id);

    List<Notification> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    List<Notification> findByUsername(String username);

    void deleteAllByUsername(String username);
}
