package com.tcdg.tcdgcompanyservice.service;

import com.tcdg.tcdgcompanyservice.dto.CreateCompanyRequest;
import com.tcdg.tcdgcompanyservice.kafka.CompanyEventProducer;
import com.tcdg.tcdgcompanyservice.model.Company;
import com.tcdg.tcdgcompanyservice.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyEventProducer eventProducer;

    public Company createCompany(UUID tenantId, CreateCompanyRequest request) {

        if (companyRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new RuntimeException("Company already exists");
        }

        Company company = Company.builder()
                .tenantId(tenantId)
                .name(request.getName())
                .registrationNumber(request.getRegistrationNumber())
                .beeLevel(request.getBeeLevel())
                .build();

        Company saved = companyRepository.save(company);

        eventProducer.publishCompanyCreated(tenantId, saved.getId());

        return saved;
    }

    public List<Company> getCompaniesByTenant(UUID tenantId) {
        return companyRepository.findByTenantId(tenantId);
    }
}