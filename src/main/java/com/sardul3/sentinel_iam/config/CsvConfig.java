package com.sardul3.sentinel_iam.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "csv")
@Getter
@Setter
public class CsvConfig {
    private List<String> headers;

}
