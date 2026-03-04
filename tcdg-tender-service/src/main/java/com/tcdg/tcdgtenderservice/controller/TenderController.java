package com.tcdg.tcdgtenderservice.controller;

import com.tcdg.tcdgtenderservice.dto.ComplianceEvaluationRequest;
import com.tcdg.tcdgtenderservice.dto.CreateTenderRequest;
import com.tcdg.tcdgtenderservice.model.ComplianceResult;
import com.tcdg.tcdgtenderservice.model.Tender;
import com.tcdg.tcdgtenderservice.service.TenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tenders")
@RequiredArgsConstructor
public class TenderController {

    private final TenderService tenderService;

    @PostMapping
    public Tender createTender(
            @RequestHeader("X-Tenant-Id") UUID tenantId,
            @RequestBody CreateTenderRequest request) {

        return tenderService.createTender(tenantId, request);
    }

    @PostMapping("/{tenderId}/evaluate")
    public ComplianceResult evaluate(
            @RequestHeader("X-Tenant-Id") UUID tenantId,
            @PathVariable UUID tenderId,
            @RequestBody ComplianceEvaluationRequest request) {

        return tenderService.evaluateCompliance(tenantId, tenderId, request);
    }

    @GetMapping
    public List<Tender> getTenders(
            @RequestHeader("X-Tenant-Id") UUID tenantId) {

        return tenderService.getTendersByTenant(tenantId);
    }
}