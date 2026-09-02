package com.course_blogging.blog_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name, description;
    private LocalDateTime createdAt;
}
