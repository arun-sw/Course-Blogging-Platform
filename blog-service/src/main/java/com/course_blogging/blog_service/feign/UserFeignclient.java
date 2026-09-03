package com.course_blogging.blog_service.feign;

import com.course_blogging.blog_service.dto.UserId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserFeignclient {
    @GetMapping("/users/{userId}")
    UserId getById(@PathVariable("userId") Long userId);

}
