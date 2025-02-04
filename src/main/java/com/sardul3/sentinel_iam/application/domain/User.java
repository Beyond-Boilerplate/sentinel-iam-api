package com.sardul3.sentinel_iam.application.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String role;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    public User(String username, String email, String role, Application application) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.application = application;
    }
}

