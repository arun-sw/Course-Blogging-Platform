package com.course_blogging.blog_service.service;

import com.course_blogging.blog_service.dto.*;
import com.course_blogging.blog_service.entity.*;
import com.course_blogging.blog_service.exception.ResourceNotFoundException;
import com.course_blogging.blog_service.repository.TagRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    // Create Tag
    @Transactional
    public TagResponse createTag(
            TagRequest request) {
        Tag tag = new Tag();
        tag.setName(request.getName());
        return toResponse(tagRepository.save(tag));
    }
    // Get All Tags
    public List<TagResponse> allTags() {
        return tagRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    // Get Tag By ID
    public TagResponse getTag(Long id) {
        return toResponse(requireTag(id));
    }
    // Update Tag
    @Transactional
    public TagResponse updateTag(Long id,TagRequest request) {
        Tag tag = requireTag(id);
        tag.setName(request.getName());
        return toResponse(tag);
    }
    // Delete Tag
    @Transactional
    public void deleteTag(Long id) {
        Tag tag = requireTag(id);
        for (Blog blog : tag.getBlogs()) {
            blog.getTags().remove(tag);
        }
        tagRepository.delete(tag);
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
    // Convert Entity → Response DTO
    private TagResponse toResponse(Tag tag) {
        return new TagResponse(
                tag.getId(),
                tag.getName(),
                tag.getCreatedAt()
        );
    }
}