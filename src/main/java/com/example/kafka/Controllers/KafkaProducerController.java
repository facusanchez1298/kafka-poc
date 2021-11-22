package com.example.kafka.Controllers;

import com.example.kafka.events.Event;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerController {
    private final KafkaTemplate<String, Event> kafkaProducer;

    public KafkaProducerController(KafkaTemplate<String, Event> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/message/{message}")
    public String postMessage(@PathVariable(name = "message") String message) {
        Event event = Event.builder().message(message).build();
        System.out.println("send to mensajes ->" + event.getMessage());
        kafkaProducer.setDefaultTopic("mensajes");
        kafkaProducer.send("mensajes", event);
        return message;
    }
}
