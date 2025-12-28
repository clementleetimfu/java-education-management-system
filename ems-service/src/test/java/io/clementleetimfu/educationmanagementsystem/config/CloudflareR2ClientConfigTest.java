package io.clementleetimfu.educationmanagementsystem.config;

import io.clementleetimfu.educationmanagementsystem.service.CloudflareR2Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Cloudflare R2 Client Config Tests")
class CloudflareR2ClientConfigTest {

    @Mock
    private S3Config s3Config;

    @Test
    void testCloudflareR2ClientBeanCreation() {
        CloudflareR2ClientConfig config = new CloudflareR2ClientConfig();

        when(s3Config.getAccessKey()).thenReturn("dummy-access-key");
        when(s3Config.getSecretKey()).thenReturn("dummy-secret-key");
        when(s3Config.getEndpoint()).thenReturn("https://dummy-account.r2.cloudflarestorage.com");

        CloudflareR2Client client = config.cloudflareR2Client(s3Config);

        assertNotNull(client, "The CloudflareR2Client bean should be created successfully");
    }
}
