package com.course_blogging.blog_service.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name = "categories")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true) private String name;
    private String description;
    @Column(nullable = false, updatable = false) private LocalDateTime createdAt;
    @PrePersist void setCreationTime() { if (createdAt == null) createdAt = LocalDateTime.now(); }
    public Long getId() { return id; } public String getName() { return name; } public void setName(String name) { this.name = name; }
    public String getDescription() { return description; } public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
