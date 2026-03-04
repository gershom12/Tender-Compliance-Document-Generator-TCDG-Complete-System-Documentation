package com.tcdg.tcdgtenderservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenderEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishTenderCreated(UUID tenantId, UUID tenderId) {
        publish("TENDER_CREATED", tenantId, tenderId, null);
    }

    public void publishComplianceEvaluated(UUID tenantId, UUID tenderId, UUID companyId) {
        publish("COMPLIANCE_EVALUATED", tenantId, tenderId, companyId);
    }

    private void publish(String type, UUID tenantId, UUID tenderId, UUID companyId) {
        try {
            Map<String, Object> event = Map.of(
                    "eventType", type,
                    "tenantId", tenantId,
                    "tenderId", tenderId,
                    "companyId", companyId
            );

            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("tender-events", payload);

        } catch (Exception e) {
            throw new RuntimeException("Failed to publish tender event", e);
        }
    }
}