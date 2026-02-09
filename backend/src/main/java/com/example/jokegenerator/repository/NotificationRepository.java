package com.example.jokegenerator.repository;

import com.example.jokegenerator.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByScheduledAtBeforeAndSentFalse(LocalDateTime time);
    
    @Query("SELECT n FROM Notification n WHERE n.scheduledAt <= ?1 AND n.sent = false")
    List<Notification> findPendingNotifications(LocalDateTime time);
    
    @Query("SELECT MAX(n.scheduledAt) FROM Notification n WHERE n.user.id = ?1 AND n.sent = true")
    LocalDateTime findLastNotificationTime(Long userId);
}