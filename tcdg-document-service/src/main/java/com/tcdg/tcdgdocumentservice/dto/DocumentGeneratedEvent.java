package com.tcdg.tcdgdocumentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentGeneratedEvent {

    private Long documentId;
    private Long tenantId;
    private String status;
}