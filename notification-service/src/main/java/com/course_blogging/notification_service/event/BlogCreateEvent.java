package com.course_blogging.notification_service.event;

import lombok.Data;

@Data
public class BlogCreateEvent {
    private Long blogId;
    private Long userId;
    private String title;
}
