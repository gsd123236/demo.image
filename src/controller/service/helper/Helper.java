//package com.image.helper;
//
//import com.image.entity.ImageData;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.util.IOUtils;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//public class Helper {
//
//    // Column names
//    public static final String[] HEADERS = {"Id", "Name", "Type", "Image"};
//    public static final String SHEET_NAME = "ImageData";
//
//    // Convert data to Excel with images
//    public static ByteArrayInputStream dataToExcel(List<ImageData> list) throws IOException {
//        Workbook workbook = new XSSFWorkbook(); // Use XSSFWorkbook instead of SXSSFWorkbook
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        try {
//            // Create a sheet
//            Sheet sheet = workbook.createSheet(SHEET_NAME);
//
//            // Create header row
//            Row headerRow = sheet.createRow(0);
//            for (int i = 0; i < HEADERS.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(HEADERS[i]);
//            }
//
//            // Set column widths
//            for (int i = 1; i < HEADERS.length - 1; i++) {
//                sheet.setColumnWidth(i, 20 * 256); // Adjust the column widths
//            }
//            sheet.setColumnWidth(3, 50 * 256); // Wider column for images
//
//            // Create data rows with images
//            int rowIndex = 1;
//            for (ImageData imageData : list) {
//                Row dataRow = sheet.createRow(rowIndex++);
//
//                // Fill in the ID, Name, and Type
//                dataRow.createCell(0).setCellValue(imageData.getId());
//                dataRow.createCell(1).setCellValue(imageData.getName());
//                dataRow.createCell(2).setCellValue(imageData.getType());
//
//                // Embed the image if present
//                if (imageData.getImageData() != null && imageData.getImageData().length > 0) {
//                    int pictureIndex = workbook.addPicture(imageData.getImageData(), Workbook.PICTURE_TYPE_JPEG);
//                    Drawing<?> drawing = sheet.createDrawingPatriarch();
//
//                    // Define anchor for the image
//                    CreationHelper helper = workbook.getCreationHelper();
//                    ClientAnchor anchor = helper.createClientAnchor();
//                    anchor.setCol1(3); // Column where the image will start
//                    anchor.setRow1(rowIndex - 1); // Row where the image will be placed
//                    anchor.setCol2(4); // Optional: Ending column
//                    anchor.setRow2(rowIndex); // Optional: Ending row
//
//                    // Attach the image to the sheet
//                    drawing.createPicture(anchor, pictureIndex);
//                } else {
//                    dataRow.createCell(3).setCellValue("No Image");
//                }
//            }
//
//            // Write to output stream
//            workbook.write(out);
//            return new ByteArrayInputStream(out.toByteArray());
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw e; // Propagate exception
//        } finally {
//            workbook.close();
//            out.close();
//        }
//    }
//}
