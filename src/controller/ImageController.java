package com.image.controller;

import com.image.service.ImageService;
//import com.image.service.ImageService.ImageNotFoundException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
//@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService service;

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
       try {
          service.uploadImage(file);
          return  ResponseEntity.ok("Image upload successfully....");
       } catch (IOException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error to upload image..");
       }
    }


    @GetMapping("/download-excel")
    public ResponseEntity<byte[]> generateExcel(@RequestParam List<Integer> id) {
        if (id.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            ByteArrayInputStream byteArrayInputStream = service.generateExcel(id);
            byte[] bytes = byteArrayInputStream.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=images.xlsx");

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
