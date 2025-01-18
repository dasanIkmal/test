package com.nstyle.test.services.service;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public interface IFileService {
    String uploadFile(String subDir,MultipartFile file) throws Exception;

    Optional<Resource> downloadFile(String fileName, String subdir) throws Exception;

    Optional<Resource> exportAllData() throws Exception;
}
