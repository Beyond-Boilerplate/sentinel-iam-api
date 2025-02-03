package com.sardul3.sentinel_iam;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // Ensures that the test configuration is used
class SentinelIamApplicationTests {

	@Test
	void contextLoads() {
	}

}
