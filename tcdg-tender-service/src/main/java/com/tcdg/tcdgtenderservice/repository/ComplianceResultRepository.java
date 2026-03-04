package com.tcdg.tcdgtenderservice.repository;

import com.tcdg.tcdgtenderservice.model.ComplianceResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ComplianceResultRepository extends JpaRepository<ComplianceResult, UUID> {

    List<ComplianceResult> findByTenderId(UUID tenderId);
}