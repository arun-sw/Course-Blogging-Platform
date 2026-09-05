package com.course_blogging.user_service.event;

import lombok.Data;

@Data
public class UserCreatedEvent {
    private Long userId;
    private String name;
    private String email;
}
