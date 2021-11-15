package com.acraftsman.h2ocloud.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
     String upload(MultipartFile multipartFile);
}
