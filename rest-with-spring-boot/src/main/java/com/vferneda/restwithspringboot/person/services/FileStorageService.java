package com.vferneda.restwithspringboot.person.services;

import com.vferneda.restwithspringboot.config.FileStorageConfig;
import com.vferneda.restwithspringboot.exception.FileStorageException;
import com.vferneda.restwithspringboot.exception.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception exc) {
            throw new FileStorageException("Could not create the directory where the upload files will be stored!", exc);
        }
    }

    public String storeFile(MultipartFile file) {
        final String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry file name contains invalid path sequence " + fileName);
            }
            final Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception exc) {
            throw new FileStorageException("Could not stored file " + fileName + ". Please try again!", exc);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            final Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            final Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
            throw new MyFileNotFoundException("File not found " + fileName);
        } catch (Exception exc) {
            throw new MyFileNotFoundException("File not found " + fileName, exc);
        }
    }
}
