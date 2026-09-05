package com.course_blogging.notification_service.repository;

import com.course_blogging.notification_service.entity.Notification;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findByUserId(Long userId);

   List<Notification> findByBlogId(Long blogId);
}
