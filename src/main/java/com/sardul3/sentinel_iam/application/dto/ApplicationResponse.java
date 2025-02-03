package com.sardul3.sentinel_iam.application.dto;

import lombok.Data;
import java.util.List;

@Data
public class ApplicationResponse {
    private Long id;
    private String name;
    private String description;
    private List<UserResponse> users;
}

