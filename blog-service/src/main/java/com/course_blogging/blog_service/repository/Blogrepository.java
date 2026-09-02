package com.course_blogging.blog_service.repository;
import com.course_blogging.blog_service.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByUserId(Long userId);
    boolean existsByCategoryId(Long categoryId);
}
