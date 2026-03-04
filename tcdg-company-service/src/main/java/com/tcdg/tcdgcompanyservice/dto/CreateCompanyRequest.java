package com.tcdg.tcdgcompanyservice.dto;

import lombok.Data;

@Data
public class CreateCompanyRequest {

    private String name;
    private String registrationNumber;
    private String beeLevel;
}