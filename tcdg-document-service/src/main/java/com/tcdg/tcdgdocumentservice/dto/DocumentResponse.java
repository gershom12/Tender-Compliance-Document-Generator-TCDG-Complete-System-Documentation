package com.tcdg.tcdgdocumentservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentResponse {

    private Long id;
    private String fileUrl;
    private String status;
}