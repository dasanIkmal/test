package com.nstyle.test.controllers;

import com.nstyle.test.services.service.IFileService;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/export")
public class ExportController {

    IFileService fileService;

    public ExportController(IFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/download/exportAll")
    public Resource exportAll() throws Exception {
        return fileService.exportAllData().orElseThrow(()-> new Exception("Error occurred while exporting the data"));
    }
}
