package com.lsd.web.service;

import org.springframework.web.multipart.MultipartFile;

public interface HandleFileUpload {
    String save(MultipartFile file);

    Boolean delete (String pathFile);

    byte[] getImage(String pathFile);
}
