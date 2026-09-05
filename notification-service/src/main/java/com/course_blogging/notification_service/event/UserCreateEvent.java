package com.course_blogging.notification_service.event;

import lombok.Data;

@Data
public class UserCreateEvent {
        private Long userId;
        private String name;
        private String email;
}
