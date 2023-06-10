package com.lsd.web.service.impl;

import com.lsd.web.exception.ErrorCode;
import com.lsd.web.exception.WebException;
import com.lsd.web.service.FileUploadService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final Path storageFolder = Paths.get("C:\\Images");


    public FileUploadServiceImpl(){
        // Creat storage folder if not exist
        try {
            Files.createDirectories(storageFolder);
        }catch (IOException ioException){
            ioException.printStackTrace();
            throw new WebException(ErrorCode.INTERNAL_SERVER);
        }
    }

    @Override
    public String storageFile(MultipartFile file) {
        try {
            // Check file is empty
            if (file.isEmpty()){
                throw new WebException(ErrorCode.ERROR_MESSAGE_DEFAULT_VN);
            }
            // Creat new file
            String filenameExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFilename = UUID.randomUUID().toString().replace("-","");
            generatedFilename = generatedFilename + "." + filenameExtension;
            Path destinationFilename = this.storageFolder.resolve(Paths.get(generatedFilename)).normalize().toAbsolutePath();
            // Save file
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream,destinationFilename, StandardCopyOption.REPLACE_EXISTING);

            return generatedFilename;

        }catch (Exception e){
            e.printStackTrace();
            throw new WebException(ErrorCode.INTERNAL_SERVER);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try{
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                return StreamUtils.copyToByteArray(resource.getInputStream());
            }else {
                throw new WebException(ErrorCode.ERROR_MESSAGE_DEFAULT_VN);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new WebException(ErrorCode.ERROR_MESSAGE_DEFAULT_VN);
        }
    }

    @Override
    public void deleteFile(String url) {
        File myObj = new File(storageFolder + "\\" + url);
        myObj.delete();
    }
}
