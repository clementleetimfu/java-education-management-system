package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.config.S3Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

/**
 * Client for interacting with Cloudflare R2 Storage using AWS SDK S3 compatibility
 */
@Slf4j
public class CloudflareR2Client {

    private final S3Config s3Config;

    private final S3Client s3Client;

    /**
     * Creates a new CloudflareR2Client with the provided configuration
     */
    public CloudflareR2Client(S3Config config) {
        this.s3Client = buildS3Client(config);
        this.s3Config = config;
    }

    /**
     * Builds and configures the S3 client with R2-specific settings
     */
    private static S3Client buildS3Client(S3Config config) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                config.getAccessKey(),
                config.getSecretKey()
        );

        S3Configuration serviceConfiguration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build();

        return S3Client.builder()
                .endpointOverride(URI.create(config.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of("auto"))
                .serviceConfiguration(serviceConfiguration)
                .build();
    }

    public String uploadObject(MultipartFile file, String key) {

        String bucketName = s3Config.getBucketName();
        String originalFilename = file.getOriginalFilename();
        String ext = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            int dotIndex = originalFilename.lastIndexOf('.');
            if (dotIndex > 0) {
                ext = originalFilename.substring(dotIndex + 1).toLowerCase();
            }
        }

        String contentType;
        switch (ext) {
            case "png":
                contentType = "image/png";
                break;
            case "jpg":
            case "jpeg":
                contentType = "image/jpeg";
                break;
            default:
                contentType = "application/octet-stream"; // fallback, should never happen
        }

        // e.g. abc/123.jpg
        String objectKey = String.format("%s/%s.%s", key, UUID.randomUUID(), ext);

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .contentType(contentType)
                    .build();
            RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

            s3Client.putObject(request, requestBody);
            return String.format("%s/%s", s3Config.getPublicUrl(), objectKey);

        } catch (S3Exception | IOException e) {
            throw new RuntimeException("Failed to upload object to bucket " + bucketName + ": " + e.getMessage(), e);
        }
    }
}