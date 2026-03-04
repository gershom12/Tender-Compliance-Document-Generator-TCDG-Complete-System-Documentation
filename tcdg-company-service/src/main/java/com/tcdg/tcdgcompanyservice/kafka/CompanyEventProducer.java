package com.tcdg.tcdgcompanyservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishCompanyCreated(UUID tenantId, UUID companyId) {
        try {
            Map<String, Object> event = Map.of(
                    "eventType", "COMPANY_CREATED",
                    "tenantId", tenantId,
                    "companyId", companyId
            );

            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("company-events", payload);

        } catch (Exception e) {
            throw new RuntimeException("Failed to publish company event", e);
        }
    }
}