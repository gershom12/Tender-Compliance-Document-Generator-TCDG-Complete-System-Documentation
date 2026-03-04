package com.tcdg.tcdgdocumentservice.kafka;

import com.tcdg.tcdgdocumentservice.dto.DocumentGeneratedEvent;
import com.tcdg.tcdgdocumentservice.model.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishGeneratedEvent(Document document) {

        DocumentGeneratedEvent event = new DocumentGeneratedEvent(
                document.getId(),
                document.getTenantId(),
                document.getStatus().name()
        );

        kafkaTemplate.send("document.generated", event);
    }
}