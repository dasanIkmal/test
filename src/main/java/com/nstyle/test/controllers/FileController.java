package com.nstyle.test.controllers;

import com.fasterxml.jackson.databind.util.ExceptionUtil;
import com.nstyle.test.exceptions.ResourceNotFoundException;
import com.nstyle.test.services.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/files")
public class FileController {

    IFileService fileService;

    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload/{subDir}")
    public String uploadFile(@PathVariable String subDir, @RequestParam("file") MultipartFile file) throws Exception {
       return fileService.uploadFile(subDir,file);
    }

    @GetMapping("/download/{fileName}")
    public Resource downloadFile(@PathVariable String fileName, @RequestParam("dir") String subdir) throws Exception {
        return fileService.downloadFile(fileName, subdir).orElseThrow(()-> new ResourceNotFoundException("File not found : "+fileName));
    }



}
