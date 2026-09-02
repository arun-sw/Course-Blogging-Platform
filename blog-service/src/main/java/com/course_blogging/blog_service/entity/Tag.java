package com.course_blogging.blog_service.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Entity @Table(name = "tags")
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true) private String name;
    @Column(nullable = false, updatable = false) private LocalDateTime createdAt;
    @ManyToMany(mappedBy = "tags") private Set<Blog> blogs = new HashSet<>();
    @PrePersist void setCreationTime() { if (createdAt == null) createdAt = LocalDateTime.now(); }
    public Long getId() { return id; } public String getName() { return name; } public void setName(String name) { this.name = name; }
    public LocalDateTime getCreatedAt() { return createdAt; } public Set<Blog> getBlogs() { return blogs; }
}
