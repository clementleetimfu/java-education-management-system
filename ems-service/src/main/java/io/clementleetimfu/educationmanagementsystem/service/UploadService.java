package io.clementleetimfu.educationmanagementsystem.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String uploadAvatar(MultipartFile multipartFile);
}
