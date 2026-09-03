package com.course_blogging.blog_service.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity @Table(name = "categories")
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @PrePersist void setCreationTime()
    { if (createdAt == null) createdAt = LocalDateTime.now(); }
}
