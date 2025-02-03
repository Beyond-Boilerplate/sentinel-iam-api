package com.sardul3.sentinel_iam.application.dto;

import lombok.Data;

@Data
public class ApplicationUploadRequest {
    private String applicationName;
    private String ownerEmail;
    private String department;
}

