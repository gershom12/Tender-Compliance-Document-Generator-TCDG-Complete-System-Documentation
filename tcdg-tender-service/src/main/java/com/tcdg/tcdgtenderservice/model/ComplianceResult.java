package com.tcdg.tcdgtenderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "compliance_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceResult {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID tenantId;

    @Column(nullable = false)
    private UUID tenderId;

    @Column(nullable = false)
    private UUID companyId;

    private BigDecimal score;

    @Column(columnDefinition = "TEXT")
    private String missingItems; // store JSON as string (H2 safe)

    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
}