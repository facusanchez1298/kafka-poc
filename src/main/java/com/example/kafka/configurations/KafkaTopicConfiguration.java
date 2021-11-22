package com.example.kafka.configurations;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {


    @Bean
    public NewTopic createEvents() {
        return new NewTopic("mensajes", 1, (short) 1);
    }
}
