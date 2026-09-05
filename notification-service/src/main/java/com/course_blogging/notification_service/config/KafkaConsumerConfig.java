package com.course_blogging.notification_service.config;

import com.course_blogging.notification_service.event.BlogCreateEvent;
import com.course_blogging.notification_service.event.BlogUpdateEvent;
import com.course_blogging.notification_service.event.UserCreateEvent;
import com.course_blogging.notification_service.event.UserUpdateEvent;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    // common kafka consumer properties
    private Map<String, Object> consumerProperties() {

        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");

        props.put(ConsumerConfig.GROUP_ID_CONFIG,"notification-group");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        return props;
    }
    @Bean
    public ConsumerFactory<String, UserCreateEvent>
    userCreateConsumerFactory() {
        JsonDeserializer<UserCreateEvent> deserializer =
                new JsonDeserializer<>(UserCreateEvent.class, false);
        deserializer.addTrustedPackages("com.course_blogging.notification_service.event");
        return new DefaultKafkaConsumerFactory<>(consumerProperties(),new StringDeserializer(),deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserCreateEvent>
    userCreateKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, UserCreateEvent>
                factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(userCreateConsumerFactory());

        return factory;
    }



    @Bean
    public ConsumerFactory<String, UserUpdateEvent>
    userUpdateConsumerFactory() {

        JsonDeserializer<UserUpdateEvent> deserializer =
                new JsonDeserializer<>(UserUpdateEvent.class, false);

        deserializer.addTrustedPackages(
                "com.course_blogging.notification_service.event"
        );

        return new DefaultKafkaConsumerFactory<>(
                consumerProperties(),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserUpdateEvent>
    userUpdateKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, UserUpdateEvent>
                factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(userUpdateConsumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, BlogCreateEvent>
    blogCreateConsumerFactory() {

        JsonDeserializer<BlogCreateEvent> deserializer =
                new JsonDeserializer<>(BlogCreateEvent.class, false);

        deserializer.addTrustedPackages(
                "com.course_blogging.notification_service.event"
        );

        return new DefaultKafkaConsumerFactory<>(
                consumerProperties(),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BlogCreateEvent>
    blogCreateKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, BlogCreateEvent>
                factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(blogCreateConsumerFactory());

        return factory;
    }
    @Bean
    public ConsumerFactory<String, BlogUpdateEvent>
    blogUpdateConsumerFactory() {

        JsonDeserializer<BlogUpdateEvent> deserializer =
                new JsonDeserializer<>(BlogUpdateEvent.class, false);

        deserializer.addTrustedPackages(
                "com.course_blogging.notification_service.event"
        );

        return new DefaultKafkaConsumerFactory<>(
                consumerProperties(),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BlogUpdateEvent>
    blogUpdateKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, BlogUpdateEvent>
                factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(blogUpdateConsumerFactory());

        return factory;
    }
}