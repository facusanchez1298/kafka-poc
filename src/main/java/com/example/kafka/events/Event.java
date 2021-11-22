package com.example.kafka.events;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class Event {
    private String message;
}
