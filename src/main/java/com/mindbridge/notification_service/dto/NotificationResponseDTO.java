package com.mindbridge.notification_service.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {
    private UUID id;
    private UUID userId;
    private String type;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;
}