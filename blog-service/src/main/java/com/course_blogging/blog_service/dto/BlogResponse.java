package com.course_blogging.blog_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime; import java.util.Set;
@Data
@AllArgsConstructor
public class BlogResponse {
    private Long id,userId,categoryId;
    private String title,content;
    private LocalDateTime createdAt;
    private Set<TagResponse> tags;

}
