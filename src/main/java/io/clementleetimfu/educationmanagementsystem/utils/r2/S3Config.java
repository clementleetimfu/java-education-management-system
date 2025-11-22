package io.clementleetimfu.educationmanagementsystem.utils.r2;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "cloudflare.r2")
public class S3Config {
    private String accountId;
    private String accessKey;
    private String secretKey;

    public String getEndpoint() {
        return String.format("https://%s.r2.cloudflarestorage.com", this.accountId);

    }
}
