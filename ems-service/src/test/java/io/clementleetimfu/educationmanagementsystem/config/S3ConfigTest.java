package io.clementleetimfu.educationmanagementsystem.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("S3 Config Test")
class S3ConfigTest {

    @Test
    @DisplayName("Test Get Endpoint Format")
    void testGetEndpoint() {
        S3Config config = new S3Config();

        config.setAccountId("test-account-id-123");

        String endpoint = config.getEndpoint();

        String expected = "https://test-account-id-123.r2.cloudflarestorage.com";
        assertEquals(expected, endpoint, "Endpoint URL should be formatted correctly with account ID");
    }
}