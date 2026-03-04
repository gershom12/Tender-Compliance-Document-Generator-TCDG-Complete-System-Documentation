package com.tcdg.tcdgtenderservice.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class CreateTenderRequest {

    private String referenceNumber;
    private String description;
    private Instant closingDate;
}