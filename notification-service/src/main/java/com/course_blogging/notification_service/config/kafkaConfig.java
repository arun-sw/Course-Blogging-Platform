package com.course_blogging.notification_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class kafkaConfig {

    public static final String UserCreatedTopic="user_created";
    public static final String UserUpdateTopic="user_update";
    public static final String BlogCreatedTopic="blog_created";
    public static final String BlogUpdateTopic="blog_updated";

    @Bean
    public NewTopic UserCreatedTopics(){
        return new NewTopic(UserCreatedTopic,1, (short) 1);
    }
    @Bean
    public NewTopic UserUpdatedTopics(){
        return new NewTopic(UserUpdateTopic,1, (short) 1);
    }
    @Bean
    public NewTopic BlogCreatedTopics(){
        return new NewTopic(BlogCreatedTopic,1, (short) 1);
    }
    @Bean
    public NewTopic BlogUpdateTopics(){
        return new NewTopic(BlogUpdateTopic,1, (short) 1);
    }
}
