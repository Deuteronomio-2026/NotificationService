package com.mindbridge.notification_service.controller;

import com.mindbridge.notification_service.dto.NotificationResponseDTO;
import com.mindbridge.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotifications(
            @PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUser(userId));
    }

    @PatchMapping("/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable UUID userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable UUID userId, @PathVariable UUID notificationId) {
        notificationService.markAsRead(notificationId, userId);
        return ResponseEntity.ok().build();
    }
}