package com.course_blogging.blog_service.controller;

import com.course_blogging.blog_service.dto.*;
import com.course_blogging.blog_service.service.TagService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
    // Create Tag
    @PostMapping
    public ResponseEntity<TagResponse> createTag(@Valid @RequestBody TagRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTag(request));
    }
    // Get All Tags
    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        return ResponseEntity.ok(tagService.allTags());
    }
    // Get Tag By ID
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTag(
            @PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTag(id));
    }
    // Update Tag
    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody TagRequest request) {

        return ResponseEntity.ok(
                tagService.updateTag(id, request)
        );
    }
    // Delete Tag
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}