package com.course_blogging.blog_service.service;

import com.course_blogging.blog_service.dto.*;
import com.course_blogging.blog_service.entity.Category;
import com.course_blogging.blog_service.exception.ResourceNotFoundException;
import com.course_blogging.blog_service.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BlogRepository blogRepository;

    public CategoryService(
            CategoryRepository categoryRepository,
            BlogRepository blogRepository) {
        this.categoryRepository = categoryRepository;
        this.blogRepository = blogRepository;
    }
    // Create Category
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return toResponse(categoryRepository.save(category));
    }
    // Get All Categories
    public List<CategoryResponse> allCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    // Get Category By ID
    public CategoryResponse getCategory(Long id) {
        return toResponse(requireCategory(id));
    }
    // Update Category
    @Transactional
    public CategoryResponse updateCategory(Long id,CategoryRequest request) {
        Category category = requireCategory(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return toResponse(category);
    }
    // Delete Category
    @Transactional
    public void deleteCategory(Long id) {
        if (blogRepository.existsByCategoryId(id)) {
            throw new IllegalArgumentException(
                    "Cannot delete a category used by a blog"
            );
        }
        categoryRepository.delete(requireCategory(id));
    }
    // Find Category
    private Category requireCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found: " + id)
                );
    }
    // Convert Entity → Response DTO
    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getCategoryId(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt()
        );
    }
}