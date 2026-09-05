package com.course_blogging.blog_service.event;

import lombok.Data;

@Data
public class BlogUpdateEvent {
    private Long blogId;
    private Long userId;
    private String title;
}
