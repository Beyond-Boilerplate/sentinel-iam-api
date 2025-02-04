package com.sardul3.sentinel_iam.application.service;

import com.sardul3.sentinel_iam.application.domain.Application;
import com.sardul3.sentinel_iam.application.domain.User;
import com.sardul3.sentinel_iam.application.dto.ApplicationMetadataResponse;
import com.sardul3.sentinel_iam.application.dto.ApplicationUploadRequest;
import com.sardul3.sentinel_iam.application.repository.ApplicationRepository;
import com.sardul3.sentinel_iam.application.repository.UserRepository;
import com.sardul3.sentinel_iam.application.exception.CsvFormatException;
import com.sardul3.sentinel_iam.application.exception.CsvProcessingException;
import com.sardul3.sentinel_iam.application.exception.DatabaseOperationException;
import com.sardul3.sentinel_iam.application.exception.DuplicateUserException;
import com.sardul3.sentinel_iam.config.CsvConfig;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final CsvConfig csvConfig;

    public Long processCsv(ApplicationUploadRequest metadata, MultipartFile file) {
        Application application = findOrCreateApplication(metadata);
        processCsvFile(file, application);
        return application.getId();
    }

    private Application findOrCreateApplication(ApplicationUploadRequest metadata) {
        try {
            return applicationRepository.findByName(metadata.getApplicationName())
                    .orElseGet(() -> applicationRepository.save(new Application(metadata.getApplicationName(), metadata.getDepartment())));
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing database while retrieving or saving application", e);
        }
    }

    private void processCsvFile(MultipartFile file, Application application) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            reader.lines()
                    .skip(1) // Skip the header
                    .map(line -> parseCsvLine(line, csvConfig.getHeaders()))
                    .map(data -> createUser(data, application))
                    .forEach(userRepository::save);
        } catch (IOException e) {
            throw new CsvProcessingException("Error processing file", e);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing database while saving users", e);
        }
    }

    private Map<String, String> parseCsvLine(String line, List<String> headers) {
        if (line == null || line.trim().isEmpty()) {
            throw new CsvFormatException("Invalid CSV format: empty line");
        }

        String[] data = line.split(",");
        if (data.length < headers.size()) {
            throw new CsvFormatException("Invalid CSV format. Expected " + headers.size() + " columns. Found: " + data.length);
        }

        return IntStream.range(0, headers.size())
                .boxed()
                .collect(Collectors.toMap(headers::get, i -> data[i].trim()));
    }

    private User createUser(Map<String, String> data, Application application) {
        String username = data.get("username");
        String email = data.get("email");
        String role = data.get("role");

        if (!isValidEmail(email)) {
            throw new CsvFormatException("Invalid email format: " + email);
        }

        if (userRepository.existsByUsernameAndApplication(username, application)) {
            throw new DuplicateUserException("User with username '" + username + "' already exists for this application.");
        }

        return new User(username, email, role, application);
    }

    public List<ApplicationMetadataResponse> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(app -> new ApplicationMetadataResponse(
                        app.getId(),
                        app.getName(),
                        app.getDepartment(),
                        app.getUsers().size()
                )).toList();
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}
