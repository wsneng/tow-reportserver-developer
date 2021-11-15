package com.acraftsman.h2ocloud.rest;

import com.acraftsman.h2ocloud.service.FileUploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {
    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
       String fileName = fileUploadService.upload(multipartFile);
       return fileName;
    }

}
