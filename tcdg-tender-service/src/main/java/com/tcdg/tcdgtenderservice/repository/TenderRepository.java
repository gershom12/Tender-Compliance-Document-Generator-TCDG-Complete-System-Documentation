package com.tcdg.tcdgtenderservice.repository;

import com.tcdg.tcdgtenderservice.model.Tender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TenderRepository extends JpaRepository<Tender, UUID> {

    List<Tender> findByTenantId(UUID tenantId);

    boolean existsByReferenceNumber(String referenceNumber);
}