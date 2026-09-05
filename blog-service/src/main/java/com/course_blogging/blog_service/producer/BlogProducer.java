package com.course_blogging.blog_service.producer;

import com.course_blogging.blog_service.event.BlogCreatedEvent;
import com.course_blogging.blog_service.event.BlogUpdateEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BlogProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BlogProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendBlogCreated(BlogCreatedEvent event){
        kafkaTemplate.send("blog_created",event.getTitle().toString(),event);
    }
    public void sendBlogUpdate(BlogUpdateEvent event1){
        kafkaTemplate.send("blog_updated",event1.getTitle().toString(),event1);
    }
}
