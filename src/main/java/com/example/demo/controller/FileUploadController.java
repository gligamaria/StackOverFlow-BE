package com.example.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class FileUploadController {

    private final String UPLOAD_DIR = "C:/Users/user/Desktop/";


    @PostMapping("/thumbnail-upload")
    public ResponseEntity<String> uploadThumbnail(@RequestParam("thumbnail") MultipartFile file) {
        try {
            // Save the file to a specific location
            String fileName = file.getOriginalFilename();
            // Specify the path where you want to save the file, e.g., "C:/uploads/"
            String savePath = "C:/Users/user/Desktop/" + fileName;
            file.transferTo(new File(savePath));

            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while uploading the file");
        }
    }

    @RequestMapping("/thumbnail/{fileName:.+}")
    public ResponseEntity<Resource> downloadThumbnail(@RequestParam String fileName) throws MalformedURLException {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
