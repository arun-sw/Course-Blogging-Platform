package com.course_blogging.blog_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "blogs")
@Setter
@Getter
public class Blog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long categoryId;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @ManyToMany
    @JoinTable(name = "blog_tags", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();
    @PrePersist void setCreationTime() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}

