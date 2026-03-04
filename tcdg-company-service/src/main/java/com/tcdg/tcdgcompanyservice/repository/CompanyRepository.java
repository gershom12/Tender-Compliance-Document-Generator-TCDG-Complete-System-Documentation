package com.tcdg.tcdgcompanyservice.repository;

import com.tcdg.tcdgcompanyservice.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    List<Company> findByTenantId(UUID tenantId);

    boolean existsByRegistrationNumber(String registrationNumber);
}