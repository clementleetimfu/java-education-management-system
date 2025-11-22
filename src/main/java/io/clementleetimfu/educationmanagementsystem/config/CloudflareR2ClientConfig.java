package io.clementleetimfu.educationmanagementsystem.config;

import io.clementleetimfu.educationmanagementsystem.utils.r2.CloudflareR2Client;
import io.clementleetimfu.educationmanagementsystem.utils.r2.S3Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudflareR2ClientConfig {

    @Bean
    public CloudflareR2Client cloudflareR2Client(S3Config s3config) {
        return new CloudflareR2Client(s3config);
    }

}
