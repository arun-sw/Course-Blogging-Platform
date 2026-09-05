package com.course_blogging.notification_service.consumer;

import com.course_blogging.notification_service.entity.Notification;
import com.course_blogging.notification_service.entity.NotificationType;
import com.course_blogging.notification_service.event.BlogCreateEvent;
import com.course_blogging.notification_service.event.BlogUpdateEvent;
import com.course_blogging.notification_service.event.UserCreateEvent;
import com.course_blogging.notification_service.event.UserUpdateEvent;
import com.course_blogging.notification_service.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.course_blogging.notification_service.config.kafkaConfig.*;

@Slf4j
@Service
public class NotificationConsumer {
    @Autowired
    private NotificationRepository notificationRepository;
    //user created listener
    @KafkaListener(topics = UserCreatedTopic,groupId = "notification-group",containerFactory = "userCreateKafkaListenerContainerFactory")
    public void consumeUserCreated(UserCreateEvent userCreateEvent) {

        Notification notification = new Notification();

        notification.setUserId(userCreateEvent.getUserId());
        notification.setBlogId(null);
        notification.setMessage("User " + userCreateEvent.getName() +" was created successfully");
        notification.setType(NotificationType.USER_CREATED);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
        log.info("User created notification stored in database");
    }
    //user update listener
    @KafkaListener(topics = UserUpdateTopic,groupId = "notification-group",containerFactory = "userUpdateKafkaListenerContainerFactory")
    public void consumeUserUpdate(UserUpdateEvent userUpdateEvent) {

        Notification notification = new Notification();
        notification.setUserId(userUpdateEvent.getUserId());
        notification.setBlogId(null);
        notification.setMessage("User " + userUpdateEvent.getName() +" was updated successfully");
        notification.setType(NotificationType.USER_UPDATED);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
        log.info("User update notification stored in database");
    }
    //blog created listener
    @KafkaListener(topics = BlogCreatedTopic,groupId = "notification-group",containerFactory = "blogCreateKafkaListenerContainerFactory")
    public void consumeBlogCreated(BlogCreateEvent blogCreateEvent) {

        Notification notification = new Notification();

        notification.setUserId(blogCreateEvent.getUserId());
        notification.setBlogId(blogCreateEvent.getBlogId());
        notification.setMessage("Blog " + blogCreateEvent.getTitle() +" was created successfully");
        notification.setType(NotificationType.BLOG_CREATED);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
        log.info("Blog created notification stored in database");
    }
    //blog update listener
    @KafkaListener(topics = BlogUpdateTopic,groupId = "notification-group",containerFactory = "blogUpdateKafkaListenerContainerFactory")
    public void consumeBlogUpdated(BlogUpdateEvent blogUpdateEvent) {

        Notification notification = new Notification();

        notification.setUserId(blogUpdateEvent.getUserId());
        notification.setBlogId(blogUpdateEvent.getBlogId());
        notification.setMessage("Blog " + blogUpdateEvent.getTitle() +" was updated successfully");
        notification.setType(NotificationType.BLOG_UPDATED);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        log.info("Blog update notification stored in database");
    }
}
