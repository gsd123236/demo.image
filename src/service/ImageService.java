package com.image.service;

import com.image.entity.ImageData;
//import com.image.helper.Helper;
import com.image.repository.ImageRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class ImageService {



    @Autowired
    private ImageRepository repo;

    // Method to upload image
    public ImageData uploadImage(MultipartFile file) throws IOException {
        ImageData image = new ImageData();
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setImageData(file.getBytes());
        return repo.save(image);
    }


    public byte[] getImage(Integer id) {
        Optional<ImageData> image = repo.findById(id);
        return image.map(ImageData::getImageData).orElse(null);
    }
    public ByteArrayInputStream generateExcel(List<Integer> id) throws IOException {
        List<ImageData> categories = repo.findAllById(id);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Image Data");
            Row headerRow = sheet.createRow(0);


            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Type");
            headerRow.createCell(3).setCellValue("Image-data");

            Drawing<?> drawing = sheet.createDrawingPatriarch();

            int rowIndex = 1;
            for (ImageData category : categories) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(category.getId());
                row.createCell(1).setCellValue(category.getName());
                row.createCell(2).setCellValue(category.getType());

                byte[] imageBytes = category.getImageData();
                if (imageBytes != null && imageBytes.length > 0) {
                    int pictureIndex = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);
                    ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 3, rowIndex - 1, 4, rowIndex);
                    drawing.createPicture(anchor, pictureIndex);
                }

                // Auto adjust column widths based on content
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }


    }

}
