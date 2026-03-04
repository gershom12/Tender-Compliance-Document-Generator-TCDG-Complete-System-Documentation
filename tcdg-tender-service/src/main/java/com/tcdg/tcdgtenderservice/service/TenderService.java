package com.tcdg.tcdgtenderservice.service;

import com.tcdg.tcdgtenderservice.dto.ComplianceEvaluationRequest;
import com.tcdg.tcdgtenderservice.dto.CreateTenderRequest;
import com.tcdg.tcdgtenderservice.kafka.TenderEventProducer;
import com.tcdg.tcdgtenderservice.model.ComplianceResult;
import com.tcdg.tcdgtenderservice.model.Tender;
import com.tcdg.tcdgtenderservice.repository.ComplianceResultRepository;
import com.tcdg.tcdgtenderservice.repository.TenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenderService {

    private final TenderRepository tenderRepository;
    private final ComplianceResultRepository complianceRepository;
    private final TenderEventProducer eventProducer;

    public Tender createTender(UUID tenantId, CreateTenderRequest request) {

        if (tenderRepository.existsByReferenceNumber(request.getReferenceNumber())) {
            throw new RuntimeException("Tender already exists");
        }

        Tender tender = Tender.builder()
                .tenantId(tenantId)
                .referenceNumber(request.getReferenceNumber())
                .description(request.getDescription())
                .closingDate(request.getClosingDate())
                .build();

        Tender saved = tenderRepository.save(tender);

        eventProducer.publishTenderCreated(tenantId, saved.getId());

        return saved;
    }

    public ComplianceResult evaluateCompliance(UUID tenantId,
                                               UUID tenderId,
                                               ComplianceEvaluationRequest request) {

        ComplianceResult result = ComplianceResult.builder()
                .tenantId(tenantId)
                .tenderId(tenderId)
                .companyId(request.getCompanyId())
                .score(request.getScore())
                .missingItems(request.getMissingItems())
                .build();

        ComplianceResult saved = complianceRepository.save(result);

        eventProducer.publishComplianceEvaluated(
                tenantId, tenderId, request.getCompanyId()
        );

        return saved;
    }

    public List<Tender> getTendersByTenant(UUID tenantId) {
        return tenderRepository.findByTenantId(tenantId);
    }
}