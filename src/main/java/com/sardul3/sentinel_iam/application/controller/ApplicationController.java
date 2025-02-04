package com.sardul3.sentinel_iam.application.controller;

import com.sardul3.sentinel_iam.application.dto.ApplicationMetadataResponse;
import com.sardul3.sentinel_iam.application.dto.ApplicationUploadRequest;
import com.sardul3.sentinel_iam.application.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Void> uploadApplicationCsv(
            @RequestPart("metadata") ApplicationUploadRequest metadata,
            @RequestPart("file") MultipartFile file) {

        Long appId = applicationService.processCsv(metadata, file);
        HttpHeaders headers = createLocationHeader("/applications/" + appId);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationMetadataResponse>> getAllApplications() {
        List<ApplicationMetadataResponse> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    private HttpHeaders createLocationHeader(String location) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(location));
        return headers;
    }
}


