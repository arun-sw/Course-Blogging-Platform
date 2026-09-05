package com.course_blogging.notification_service.service;

import com.course_blogging.notification_service.entity.Notification;
import com.course_blogging.notification_service.repository.NotificationRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public @Nullable List<Notification> GetAllNotification() {
        return notificationRepository.findAll();
    }
    public @Nullable List<Notification> GetByUserId(Long userId) {
     return notificationRepository.findByUserId(userId);
    }

    public @Nullable List<Notification> GetByBlogId(Long blogId) {
        return notificationRepository.findByBlogId(blogId);
    }
}
