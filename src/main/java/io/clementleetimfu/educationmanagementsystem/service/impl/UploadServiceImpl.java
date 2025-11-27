package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.service.UploadService;
import io.clementleetimfu.educationmanagementsystem.service.CloudflareR2Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private CloudflareR2Client cloudflareR2Client;

    @Override
    public String uploadAvatar(MultipartFile multipartFile) {
        return cloudflareR2Client.uploadObject(multipartFile, "avatar");
    }
}
