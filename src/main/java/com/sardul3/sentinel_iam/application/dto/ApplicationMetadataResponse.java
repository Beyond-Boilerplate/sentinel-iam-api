package com.sardul3.sentinel_iam.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationMetadataResponse {
    private Long id;
    private String name;
    private String department;
    private int userCount;
}
