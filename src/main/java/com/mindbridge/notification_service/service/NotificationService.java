package com.mindbridge.notification_service.service;

import com.mindbridge.notification_service.dto.NotificationResponseDTO;
import com.mindbridge.notification_service.model.Notification;
import com.mindbridge.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void createNotification(UUID userId, String type, String message) {
        Notification notification = Notification.builder()
                .userId(userId)
                .type(type)
                .message(message)
                .read(false)
                .createdAt(ZonedDateTime.now())
                .build();
        notificationRepository.save(notification);
    }

    public List<NotificationResponseDTO> getNotificationsByUser(UUID userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public void markAllAsRead(UUID userId) {
        List<Notification> unread = notificationRepository.findByUserIdAndReadFalse(userId);
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
    }

    @Transactional
    public void markAsRead(UUID notificationId, UUID userId) {
        Notification notif = notificationRepository.findByIdAndUserId(notificationId, userId)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notif.setRead(true);
        notificationRepository.save(notif);
    }



    private NotificationResponseDTO toDTO(Notification n) {
        return NotificationResponseDTO.builder()
                .id(n.getId())
                .userId(n.getUserId())
                .type(n.getType())
                .message(n.getMessage())
                .read(n.isRead())
                .createdAt(n.getCreatedAt())
                .build();
    }
}