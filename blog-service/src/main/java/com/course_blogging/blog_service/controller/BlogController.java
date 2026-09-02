package com.course_blogging.blog_service.controller;

import com.course_blogging.blog_service.dto.*;
import com.course_blogging.blog_service.service.BlogService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    private final BlogService blogService;
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }
    // Create Blog
    @PostMapping
    public ResponseEntity<BlogResponse> createBlog(
            @Valid @RequestBody BlogRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(blogService.createBlog(request));
    }
    // Get All Blogs
    @GetMapping
    public ResponseEntity<List<BlogResponse>> getAllBlogs() {
        return ResponseEntity.ok(blogService.allBlogs());
    }
    // Get Blog By ID
    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlog(id));
    }
    // Get Blogs By User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BlogResponse>> getBlogsByUser(
            @PathVariable Long userId) {
        return ResponseEntity.ok(blogService.blogsByUser(userId));
    }
    // Update Blog
    @PutMapping("/{id}")
    public ResponseEntity<BlogResponse> updateBlog(
            @PathVariable Long id,
            @Valid @RequestBody BlogRequest request) {
        return ResponseEntity.ok(
                blogService.updateBlog(id, request)
        );
    }
    // Delete Blog
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(
            @PathVariable Long id,
            @RequestParam Long userId) {

        blogService.deleteBlog(id, userId);
        return ResponseEntity.noContent().build();
    }
    // Add Tags To Blog
    @PostMapping("/{id}/tags")
    public ResponseEntity<BlogResponse> addTags(
            @PathVariable Long id,
            @Valid @RequestBody TagIdsRequest request) {

        return ResponseEntity.ok(blogService.addTags(id, request));
    }
}