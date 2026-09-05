package com.course_blogging.blog_service.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "tags")
@Setter
@Getter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @ManyToMany(mappedBy = "tags")
    private Set<Blog> blogs = new HashSet<>();
    @PrePersist
    void setCreationTime() { if (createdAt == null) createdAt = LocalDateTime.now(); }
}
