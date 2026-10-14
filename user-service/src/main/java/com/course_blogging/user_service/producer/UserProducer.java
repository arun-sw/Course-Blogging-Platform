package com.course_blogging.user_service.producer;

import com.course_blogging.user_service.event.UserCreatedEvent;
import com.course_blogging.user_service.event.UserUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserCreated(UserCreatedEvent event){
        log.info("Sending UserCreatedEvent: userId={}", event.getUserId());
        kafkaTemplate.send("user_created",event.getUserId().toString(),event).whenComplete((result, ex) -> {
        });

    }
    public void sendUserUpdate(UserUpdateEvent event){
        kafkaTemplate.send("user_update",event.getUserId().toString(),event);
    }
}
