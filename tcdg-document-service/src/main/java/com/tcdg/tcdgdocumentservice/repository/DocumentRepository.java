package com.tcdg.tcdgdocumentservice.repository;

import com.tcdg.tcdgdocumentservice.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}