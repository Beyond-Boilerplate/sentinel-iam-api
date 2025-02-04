package com.sardul3.sentinel_iam.application.repository;

import com.sardul3.sentinel_iam.application.domain.Application;
import com.sardul3.sentinel_iam.application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameAndApplication(String username, Application application);
}

