package com.course_blogging.user_service.DTO;

import lombok.Data;

@Data
public class AutheticationResponce {
    private final String token;
    private final String tokenType;
    private final Long userId;
}
