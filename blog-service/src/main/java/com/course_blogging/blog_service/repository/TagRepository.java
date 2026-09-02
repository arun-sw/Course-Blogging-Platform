package com.course_blogging.blog_service.repository;
import com.course_blogging.blog_service.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TagRepository extends JpaRepository<Tag, Long> {

}
