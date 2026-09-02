package com.course_blogging.blog_service.repository;

import com.course_blogging.blog_service.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Blogrepository extends JpaRepository<BlogEntity ,Long> {

}
