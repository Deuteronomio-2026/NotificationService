package com.mindbridge.notification_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    // ─── Eventos de Ofertas ───────────────────────────────────────────────────

    @RabbitListener(queues = "offer.created.queue")
    public void onOfferCreated(Map<String, Object> payload) {
        log.info("Evento recibido: offer.created -> {}", payload);
        UUID userId = extractUUID(payload, "psychologistId");
        if (userId != null) {
            notificationService.createNotification(
                    userId,
                    "offer.created",
                    "Se ha creado una nueva oferta: " + payload.get("title")
            );
        }
    }

    @RabbitListener(queues = "offer.cancelled.queue")
    public void onOfferCancelled(Map<String, Object> payload) {
        log.info("Evento recibido: offer.cancelled -> {}", payload);
        UUID userId = extractUUID(payload, "psychologistId");
        if (userId != null) {
            notificationService.createNotification(
                    userId,
                    "offer.cancelled",
                    "La oferta ha sido cancelada: " + payload.get("title")
            );
        }
    }

    @RabbitListener(queues = "offer.taken.queue")
    public void onOfferTaken(Map<String, Object> payload) {
        log.info("Evento recibido: offer.taken -> {}", payload);
        UUID userId = extractUUID(payload, "psychologistId");
        if (userId != null) {
            notificationService.createNotification(
                    userId,
                    "offer.taken",
                    "Un psicólogo ha tomado la oferta: " + payload.get("title")
            );
        }
    }

    // ─── Eventos de Sesiones Grupales ─────────────────────────────────────────

    @RabbitListener(queues = "session.requested.queue")
    public void onSessionRequested(Map<String, Object> payload) {
        log.info("Evento recibido: session.requested -> {}", payload);
        UUID userId = extractUUID(payload, "psychologistId");
        if (userId != null) {
            notificationService.createNotification(
                    userId,
                    "session.requested",
                    "Nueva sesión grupal solicitada: " + payload.get("title")
            );
        }
    }

    @RabbitListener(queues = "session.approved.queue")
    public void onSessionApproved(Map<String, Object> payload) {
        log.info("Evento recibido: session.approved -> {}", payload);
        UUID userId = extractUUID(payload, "psychologistId");
        if (userId != null) {
            notificationService.createNotification(
                    userId,
                    "session.approved",
                    "Tu sesión grupal fue aprobada: " + payload.get("title")
            );
        }
    }

    @RabbitListener(queues = "session.enrolled.queue")
    public void onSessionEnrolled(Map<String, Object> payload) {
        log.info("Evento recibido: session.enrolled -> {}", payload);
        UUID userId = extractUUID(payload, "psychologistId");
        if (userId != null) {
            notificationService.createNotification(
                    userId,
                    "session.enrolled",
                    "Un paciente se inscribió en tu sesión: " + payload.get("title")
            );
        }
    }

    @RabbitListener(queues = "session.cancelled.queue")
    public void onSessionCancelled(Map<String, Object> payload) {
        log.info("Evento recibido: session.cancelled -> {}", payload);
        UUID userId = extractUUID(payload, "psychologistId");
        if (userId != null) {
            notificationService.createNotification(
                    userId,
                    "session.cancelled",
                    "La sesión grupal fue cancelada: " + payload.get("title")
            );
        }
    }

    // ─── Eventos de Scheduling ────────────────────────────────────────────────

    @RabbitListener(queues = "scheduling.session.booked.queue")
    public void onSchedulingSessionBooked(Map<String, Object> payload) {
        log.info("Evento recibido: scheduling.session.booked -> {}", payload);
        UUID userId = extractUUID(payload, "patientId");
        if (userId != null) {
            notificationService.createNotification(
                    userId,
                    "scheduling.session.booked",
                    "Tu sesión ha sido confirmada para: " + payload.get("date")
            );
        }
    }

    @RabbitListener(queues = "scheduling.session.cancelled.queue")
    public void onSchedulingSessionCancelled(Map<String, Object> payload) {
        log.info("Evento recibido: scheduling.session.cancelled -> {}", payload);
        UUID userId = extractUUID(payload, "patientId");
        if (userId != null) {
            notificationService.createNotification(
                    userId,
                    "scheduling.session.cancelled",
                    "Tu sesión del " + payload.get("date") + " fue cancelada"
            );
        }
    }

    @RabbitListener(queues = "scheduling.session.rescheduled.queue")
    public void onSchedulingSessionRescheduled(Map<String, Object> payload) {
        log.info("Evento recibido: scheduling.session.rescheduled -> {}", payload);
        UUID userId = extractUUID(payload, "patientId");
        if (userId != null) {
            notificationService.createNotification(
                    userId,
                    "scheduling.session.rescheduled",
                    "Tu sesión fue reprogramada para: " + payload.get("date")
            );
        }
    }

    // ─── Helper ───────────────────────────────────────────────────────────────

    private UUID extractUUID(Map<String, Object> payload, String key) {
        try {
            Object value = payload.get(key);
            if (value == null) return null;
            return UUID.fromString(value.toString());
        } catch (Exception e) {
            log.warn("No se pudo extraer UUID del campo '{}': {}", key, e.getMessage());
            return null;
        }
    }
}