package com.tcdg.tcdgdocumentservice.kafka;

import com.tcdg.tcdgdocumentservice.dto.DocumentRequestEvent;
import com.tcdg.tcdgdocumentservice.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentGenerationListener {

    private final DocumentService documentService;

    @KafkaListener(
            topics = "tender.document.generate",
            groupId = "document-service-group"
    )
    public void consume(DocumentRequestEvent event) {

        documentService.generateDocument(event);
    }
}