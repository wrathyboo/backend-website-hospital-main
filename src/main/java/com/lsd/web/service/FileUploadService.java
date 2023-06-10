package com.lsd.web.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileUploadService {
    String storageFile (MultipartFile file);
    byte[] readFileContent(String fileName);
    public void deleteFile(String url);
}
