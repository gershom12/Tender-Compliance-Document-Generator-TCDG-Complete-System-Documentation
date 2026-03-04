package com.tcdg.tcdgdocumentservice.controller;

import com.tcdg.tcdgdocumentservice.model.Document;
import com.tcdg.tcdgdocumentservice.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentRepository repository;

    @GetMapping("/{id}")
    public Document getDocument(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }
}