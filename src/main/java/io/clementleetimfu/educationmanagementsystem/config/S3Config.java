package io.clementleetimfu.educationmanagementsystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "cloudflare.r2")
public class S3Config {
    private String accountId;
    private String bucketName;
    private String accessKey;
    private String secretKey;
    private String publicUrl;

    public String getEndpoint() {
        return String.format("https://%s.r2.cloudflarestorage.com", this.accountId);

    }
}
