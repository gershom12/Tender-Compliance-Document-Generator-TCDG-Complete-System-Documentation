package com.tcdg.tcdgauthservice.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishUserCreatedEvent(String username) {
        kafkaTemplate.send("user-events", "UserCreated:" + username);
        System.out.println("Published user-created event: " + username);
    }
}