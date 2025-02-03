package com.sardul3.sentinel_iam.application.service;

import com.sardul3.sentinel_iam.application.domain.Application;
import com.sardul3.sentinel_iam.application.domain.User;
import com.sardul3.sentinel_iam.application.dto.ApplicationMetadataResponse;
import com.sardul3.sentinel_iam.application.dto.ApplicationUploadRequest;
import com.sardul3.sentinel_iam.application.repository.ApplicationRepository;
import com.sardul3.sentinel_iam.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    public Long processCsv(ApplicationUploadRequest metadata, MultipartFile file) {
        Application application = applicationRepository.findByName(metadata.getApplicationName())
                .orElseGet(() -> applicationRepository.save(new Application(metadata.getApplicationName(), metadata.getDepartment())));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isHeader = true; // Skip the header row

            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trim any unwanted spaces

                // Skip the header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                // Ensure there are exactly 3 elements (username, email, role)
                if (data.length < 3) {
                    throw new IllegalArgumentException("Invalid CSV format. Expected 3 columns: username, email, role. Found: " + data.length);
                }

                String username = data[0].trim();
                String email = data[1].trim();
                String role = data[2].trim();

                User user = new User(null, username, email, role, application);
                userRepository.save(user);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing file", e);
        }
        return application.getId();
    }

    public List<ApplicationMetadataResponse> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(app -> new ApplicationMetadataResponse(
                        app.getId(),
                        app.getName(),
                        app.getDepartment(),
                        app.getUsers().size()
                )).collect(Collectors.toList());
    }
}
