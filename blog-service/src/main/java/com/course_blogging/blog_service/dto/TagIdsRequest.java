package com.course_blogging.blog_service.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;
@Data
public class TagIdsRequest {
    @NotEmpty
    private Set<Long> tagIds;
}
