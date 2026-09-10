package com.course_blogging.blog_service.service;

import com.course_blogging.blog_service.dto.*;
import com.course_blogging.blog_service.entity.*;
import com.course_blogging.blog_service.event.BlogCreatedEvent;
import com.course_blogging.blog_service.event.BlogUpdateEvent;
import com.course_blogging.blog_service.exception.*;
import com.course_blogging.blog_service.feign.UserFeignclient;
import com.course_blogging.blog_service.producer.BlogProducer;
import com.course_blogging.blog_service.repository.*;

import com.course_blogging.blog_service.security.UserServiceClient;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BlogService {

    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserServiceClient userServiceClient;
    private final BlogProducer blogProducer;

    public BlogService(
            BlogRepository blogRepository,
            CategoryRepository categoryRepository,
            TagRepository tagRepository,
            UserFeignclient userFeignClient, UserServiceClient userServiceClient, BlogProducer blogProducer){

        this.blogRepository = blogRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.userServiceClient = userServiceClient;
        this.blogProducer = blogProducer;
    }
    // Create Blog
    @Transactional
    public BlogResponse createBlog(BlogRequest request) {

        // Check user exists
        userServiceClient.requireUser(request.getUserId());

        // Check category exists
        requireCategory(request.getCategoryId());

        // Create blog
        Blog blog = new Blog();

        blog.setUserId(request.getUserId());
        blog.setCategoryId(request.getCategoryId());
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());

        // Set tags
        Set<Tag> tags = new HashSet<>(
                tagRepository.findAllById(request.getTagIds())
        );

        blog.setTags(tags);

        Blog savedBlog = blogRepository.save(blog);

        BlogCreatedEvent event = new BlogCreatedEvent();

        event.setBlogId(savedBlog.getBlogId());
        event.setUserId(savedBlog.getUserId());
        event.setTitle(savedBlog.getTitle());

        blogProducer.sendBlogCreated(event);
        return toResponse(savedBlog);
    }
    // Get All Blogs
    public List<BlogResponse> allBlogs() {
        return blogRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    // Get Blog By ID
    public BlogResponse getBlog(Long id) {
        return toResponse(requireBlog(id));
    }
    // Get Blogs By User
    public List<BlogResponse> blogsByUser(Long userId) {
        return blogRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }
    // Update Blog
    @Transactional
    public BlogResponse updateBlog(Long id, BlogRequest request) {

        Blog blog = requireBlog(id);
        verifyOwner(blog, request.getUserId());
        requireCategory(request.getCategoryId());

        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setCategoryId(request.getCategoryId());

        Blog updatedBlog = blogRepository.save(blog);

        BlogUpdateEvent event = new BlogUpdateEvent();

        event.setBlogId(updatedBlog.getBlogId());
        event.setUserId(updatedBlog.getUserId());
        event.setTitle(updatedBlog.getTitle());

        blogProducer.sendBlogUpdate(event);

        return toResponse(updatedBlog);
    }
    // Delete Blog
    @Transactional
    public void deleteBlog(Long id, Long userId) {
        Blog blog = requireBlog(id);
        verifyOwner(
                blog,
                userId
        );
        blogRepository.delete(blog);
    }
    // Find Blog
    private Blog requireBlog(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Blog not found: " + id)
                );
    }
    // Check Category
    private void requireCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Category not found: " + id
            );
        }
    }
    // Find Tag
    private Tag requireTag(Long id) {
        return tagRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Tag not found: " + id));
    }

    // Verify Blog Owner
    private void verifyOwner(
            Blog blog,
            Long userId) {

        if (!blog.getUserId().equals(userId)) {

            throw new ForbiddenOperationException(
                    "Only the blog owner can perform this action"
            );
        }
    }
    // Convert Entity → Response DTO
    private BlogResponse toResponse(Blog blog) {

        Set<Tag> tags = new HashSet<>(blog.getTags());
        Set<TagResponse> tagResponses = tags.stream()
                .map(tag ->new TagResponse(
                                tag.getTagId(),
                                tag.getName(),
                                tag.getCreatedAt()
                        )
                )
                .collect(Collectors.toSet());

        return new BlogResponse(
                blog.getBlogId(),
                blog.getUserId(),
                blog.getCategoryId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getCreatedAt(),
                tagResponses
        );
    }
}