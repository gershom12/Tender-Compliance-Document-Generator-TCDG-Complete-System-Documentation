package com.tcdg.tcdgdocumentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(
        name = "company-service",
        url = "${services.company-service-url}"
)
public interface CompanyClient {

    @GetMapping("/api/companies/{id}/data")
    Map<String, Object> getCompanyData(@PathVariable("id") Long id);
}