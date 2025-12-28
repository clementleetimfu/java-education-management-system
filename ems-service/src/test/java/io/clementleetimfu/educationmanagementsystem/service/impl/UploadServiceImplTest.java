package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.service.CloudflareR2Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Upload Service Implementation Tests")
class UploadServiceImplTest {

    @Mock
    private CloudflareR2Client cloudflareR2Client;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private UploadServiceImpl uploadServiceImpl;

    @Test
    @DisplayName("Test Upload Avatar Returns Url")
    void testUploadAvatarReturnsUrl() {
        String expectedUrl = "https://example.com/avatar/test.jpg";
        when(cloudflareR2Client.uploadObject(any(MultipartFile.class), eq("avatar"))).thenReturn(expectedUrl);

        String result = uploadServiceImpl.uploadAvatar(multipartFile);

        assertEquals(expectedUrl, result);
        verify(cloudflareR2Client, times(1)).uploadObject(any(MultipartFile.class), eq("avatar"));
    }
}
