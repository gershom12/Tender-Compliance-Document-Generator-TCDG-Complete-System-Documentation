package com.tcdg.tcdgcompanyservice.controller;

import com.tcdg.tcdgcompanyservice.dto.CreateCompanyRequest;
import com.tcdg.tcdgcompanyservice.model.Company;
import com.tcdg.tcdgcompanyservice.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public Company createCompany(
            @RequestHeader("X-Tenant-Id") UUID tenantId,
            @RequestBody CreateCompanyRequest request) {

        return companyService.createCompany(tenantId, request);
    }

    @GetMapping
    public List<Company> getCompanies(
            @RequestHeader("X-Tenant-Id") UUID tenantId) {

        return companyService.getCompaniesByTenant(tenantId);
    }
}