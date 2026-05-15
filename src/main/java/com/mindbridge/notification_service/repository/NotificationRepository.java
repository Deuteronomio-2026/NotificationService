package com.mindbridge.notification_service.repository;

import com.mindbridge.notification_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId);
    List<Notification> findByUserIdAndReadFalse(UUID userId);
    Optional<Notification> findByIdAndUserId(UUID id, UUID userId);
}