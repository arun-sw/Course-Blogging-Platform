package com.course_blogging.blog_service.controller;

import com.course_blogging.blog_service.entity.BlogEntity;
import com.course_blogging.blog_service.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bloggers/")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping
    public BlogEntity CreateBlog(@RequestBody BlogEntity blog){
        return blogService.CreateBlog(blog);
    }

}
