package com.tcdg.tcdgdocumentservice.service;

import com.tcdg.tcdgdocumentservice.config.MinioProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final S3Client s3Client;
    private final MinioProperties properties;

    public String uploadFile(byte[] fileBytes, String fileName) {

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(fileName)
                .contentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(fileBytes));

        return properties.getEndpoint()
                + "/"
                + properties.getBucket()
                + "/"
                + fileName;
    }
}