package com.course_blogging.notification_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = true)
    private Long blogId;
    @Column(columnDefinition = "TEXT")
    private String message;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
