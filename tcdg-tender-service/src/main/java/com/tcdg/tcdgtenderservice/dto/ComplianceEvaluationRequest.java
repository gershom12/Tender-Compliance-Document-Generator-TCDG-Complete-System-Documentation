package com.tcdg.tcdgtenderservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ComplianceEvaluationRequest {

    private UUID companyId;
    private BigDecimal score;
    private String missingItems; // JSON string
}