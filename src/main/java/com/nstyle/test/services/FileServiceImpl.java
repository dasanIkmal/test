package com.nstyle.test.services;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.nstyle.test.entities.OrderEntity;
import com.nstyle.test.entities.UserEntity;
import com.nstyle.test.repositories.OrderRepository;
import com.nstyle.test.repositories.UserRepository;
import com.nstyle.test.services.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${file.upload.dir:uploads}")
    String uploadDir;

    @Override
    public String uploadFile(String subDir, MultipartFile file) throws Exception {
        try {
            // Extract original file extension
            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            // Generate a new file name using UUID
            String newFileName = UUID.randomUUID().toString() + fileExtension;

            // Define the target directory and file location
            Path targetDir = Paths.get(uploadDir).resolve(subDir);
            Path targetLocation = targetDir.resolve(newFileName);

            // Create directories if they don't exist
            Files.createDirectories(targetDir);

            // Copy the file to the target location
            Files.copy(file.getInputStream(), targetLocation);

            // Return the full path of the uploaded file
            return targetLocation.toAbsolutePath().toString();
        } catch (IOException e) {
            String error = "File upload failed";
            log.error(error, e.getMessage());
            throw new Exception(error, e);
        }
    }



    @Override
    public Optional<Resource> downloadFile(String fileName, String subdir) throws Exception {
        try {

            Path filePath = Paths.get(uploadDir).resolve(subdir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return Optional.of(resource);
            } else {
                return Optional.empty();
            }
        } catch (MalformedURLException e) {
            String error = "Unable to find file in the server directory.";
            log.error(error,e.getMessage());
            throw new Exception(error,e.getCause());
        }
    }

    @Override
    public Optional<Resource> exportAllData() throws Exception {

        List<UserEntity> users = userRepository.findAll();
        String subDir = "allData";

        // Generate a unique file name with date-time
        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "users_with_orders_" + timestamp + ".pdf";

        // Resolve the target directory and file path
        Path targetDir = Paths.get(uploadDir).resolve(subDir);
        Path targetFile = targetDir.resolve(fileName);

        // Ensure the directory exists
        Files.createDirectories(targetDir);

        // Create the PDF
        PdfWriter writer = new PdfWriter(new FileOutputStream(targetFile.toFile()));
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        document.add(new Paragraph("Users and Their Orders").setBold().setFontSize(16).setMarginBottom(20));

        for (UserEntity user : users) {

            document.add(new Paragraph("User ID: " + user.getUserId()));
            document.add(new Paragraph("Name: " + user.getUserName()));
            document.add(new Paragraph("Email: " + user.getUserEmail()));
            document.add(new Paragraph("DOB: " + user.getUserDOB()).setMarginBottom(10));

            List<OrderEntity> orders = orderRepository.findByUser_UserId(user.getUserId());

            Table table = new Table(4);
            table.addHeaderCell(new Cell().add(new Paragraph("Order ID").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Customer ID").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Total Bill").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Message").setBold()));

            for (OrderEntity order : orders) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getId()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getCustomer().getId()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getTotalBill()))));
                table.addCell(new Cell().add(new Paragraph(order.getMessage())));
            }

            document.add(table.setMarginBottom(20));
        }

        document.close();

        return downloadFile(fileName, subDir);
    }

}
