package com.tcdg.tcdgdocumentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "template-service",
        url = "${services.template-service-url}"
)
public interface TemplateClient {

    @GetMapping("/api/templates/{id}")
    String getTemplate(@PathVariable("id") Long id);
}