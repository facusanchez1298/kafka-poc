package com.example.kafka.Controller;

import com.example.kafka.events.Event;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "event-listener 2", topics = {"mensajes"})
public class KafkaControllerListener2 {

    private final TaskExecutor exec = new SimpleAsyncTaskExecutor();

    @KafkaHandler
    public void event(@Payload Event event) {
        System.out.println("2 Received: " + event.getMessage());
    }


    @KafkaHandler(isDefault = true)
    public void listenDefault(Object object) {
        System.out.println("que mierda pasa?" + object);
    }
}
