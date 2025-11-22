package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.service.UploadService;
import io.clementleetimfu.educationmanagementsystem.utils.r2.CloudflareR2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private CloudflareR2Client cloudflareR2Client;

    @Value("${cloudflare.r2.bucketName}")
    private String bucketName;

    @Override
    public String uploadAvatar(MultipartFile multipartFile) {
        return cloudflareR2Client.uploadObject(multipartFile, bucketName, "avatar");
    }
}
