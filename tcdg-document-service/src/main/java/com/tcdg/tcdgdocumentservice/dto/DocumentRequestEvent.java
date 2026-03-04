package com.tcdg.tcdgdocumentservice.dto;

import lombok.Data;

@Data
public class DocumentRequestEvent {

    private Long tenantId;
    private Long companyId;
    private Long tenderId;
    private Long templateId;
}