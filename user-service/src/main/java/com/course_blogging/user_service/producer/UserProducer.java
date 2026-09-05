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

            if (ex == null) {
                log.info(
                        "SUCCESS: UserCreatedEvent sent to Kafka. topic={}, partition={}, offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                );
            } else {
                log.error("FAILED: UserCreatedEvent was NOT sent to Kafka",ex
                );
            }
        });

    }
    public void sendUserUpdate(UserUpdateEvent event){
        kafkaTemplate.send("user_update",event.getUserId().toString(),event);
    }
}
