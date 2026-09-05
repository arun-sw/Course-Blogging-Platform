package com.course_blogging.notification_service.controller;

import com.course_blogging.notification_service.entity.Notification;
import com.course_blogging.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> GetAllNotification(){
        return ResponseEntity.ok(notificationService.GetAllNotification());
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> GetByUserId(@PathVariable Long userId){
        return  ResponseEntity.ok(notificationService.GetByUserId(userId));
    }
    @GetMapping("blog/{blogId}")
    public ResponseEntity<List<Notification>> GetByBlogId(@PathVariable Long blogId){
        return  ResponseEntity.ok(notificationService.GetByBlogId(blogId));
    }

}
