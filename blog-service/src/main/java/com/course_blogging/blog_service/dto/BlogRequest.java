package com.course_blogging.blog_service.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class BlogRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long categoryId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    @NotEmpty(message = "At least one tag is required")
    private Set<Long> tagIds;

}
