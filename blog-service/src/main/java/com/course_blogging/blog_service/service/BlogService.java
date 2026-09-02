package com.course_blogging.blog_service.service;

import com.course_blogging.blog_service.dto.*;
import com.course_blogging.blog_service.entity.*;
import com.course_blogging.blog_service.exception.*;
import com.course_blogging.blog_service.repository.*;

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

    public BlogService(
            BlogRepository blogRepository,
            CategoryRepository categoryRepository,
            TagRepository tagRepository) {

        this.blogRepository = blogRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }
    // Create Blog
    @Transactional
    public BlogResponse createBlog(BlogRequest request) {
        requireCategory(request.getCategoryId());
        Blog blog = new Blog();
        blog.setUserId(request.getUserId());
        blog.setCategoryId(request.getCategoryId());
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());

        return toResponse(
                blogRepository.save(blog)
        );
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
    public BlogResponse updateBlog(Long id,BlogRequest request) {
        Blog blog = requireBlog(id);
        verifyOwner(blog,request.getUserId());
        requireCategory(request.getCategoryId());
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setCategoryId(request.getCategoryId());
        return toResponse(blog);
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
    // Add Tags To Blog
    @Transactional
    public BlogResponse addTags(
            Long id,
            TagIdsRequest request) {
        Blog blog = requireBlog(id);
        Set<Tag> tags = request.getTagIds()
                .stream()
                .map(this::requireTag)
                .collect(Collectors.toSet());
        blog.getTags().addAll(tags);
        return toResponse(blog);
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

        return tagRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tag not found: " + id
                        )
                );
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

        Set<TagResponse> tagResponses = blog.getTags()
                .stream()
                .map(tag ->new TagResponse(
                                tag.getId(),
                                tag.getName(),
                                tag.getCreatedAt()
                        )
                )
                .collect(Collectors.toSet());

        return new BlogResponse(
                blog.getId(),
                blog.getUserId(),
                blog.getCategoryId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getCreatedAt(),
                tagResponses
        );
    }
}