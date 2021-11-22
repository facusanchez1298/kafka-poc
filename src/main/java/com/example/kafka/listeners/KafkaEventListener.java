package com.example.kafka.listeners;

import com.example.kafka.events.Event;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@org.springframework.kafka.annotation.KafkaListener(id = "event-listener", topics = {"mensajes"})
public class KafkaEventListener {

    private final TaskExecutor exec = new SimpleAsyncTaskExecutor();

    @KafkaHandler
    public void event(@Payload Event event) {
        System.out.println("1 Received: " + event.getMessage());
    }


    @KafkaHandler(isDefault = true)
    public void listenDefault(Object object) {
        System.out.println("que mierda pasa?" + object);
    }
}
