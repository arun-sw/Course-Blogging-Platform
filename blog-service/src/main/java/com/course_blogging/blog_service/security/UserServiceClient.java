package com.course_blogging.blog_service.security;

import com.course_blogging.blog_service.dto.UserId;
import com.course_blogging.blog_service.exception.ResourceNotFoundException;
import com.course_blogging.blog_service.feign.UserFeignclient;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class UserServiceClient {

    private final UserFeignclient userFeignClient;

    public UserServiceClient(UserFeignclient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @CircuitBreaker(name = "userService",fallbackMethod = "userServiceFallback")
    public void requireUser(Long userId) {

        try {
            userFeignClient.getById(userId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("User not found: " + userId);
        }
    }
    public void userServiceFallback(Long userId,Throwable throwable) throws Throwable {
        if (throwable instanceof ResourceNotFoundException || throwable instanceof FeignException.NotFound) {
            throw throwable;
        }
        throw new RuntimeException("User Service is currently unavailable. Please try again later.");
    }
}