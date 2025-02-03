package com.sardul3.sentinel_iam.application.repository;

import com.sardul3.sentinel_iam.application.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByName(String name);
}

