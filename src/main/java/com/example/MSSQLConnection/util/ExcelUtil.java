package com.example.MSSQLConnection.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

public class ExcelUtil {

    static Logger logger = Logger.getLogger(ExcelUtil.class.getName());

    public static void createExcelFile(String jsonData) throws IOException {
        createJsonFile(jsonData);

        ObjectMapper om = new ObjectMapper();
        try {
            JsonNode node = om.readTree(new File("D:/input.json"));
            JsonNode header = node.get("header");
            Iterator<JsonNode> it = header.iterator();
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(" Employee Details");
            Row row = sheet.createRow(0);
            int colNum = 0;
            while(it.hasNext()) {
                Cell cell = row.createCell(colNum++);
                cell.setCellValue(it.next().asText());
            }
            JsonNode body = node.get("body");
            int rowNum = 1;
            colNum = 0;
            int i = 0;
            JsonNode rowNode;
            while(i < body.size()) {
                rowNode = body.get(i++);
                Row bodyRow = sheet.createRow(rowNum++);
                Cell ageCell = bodyRow.createCell(colNum++);
                Cell nameCell = bodyRow.createCell(colNum++);
                Cell surnameCell = bodyRow.createCell(colNum++);

                ageCell.setCellValue(rowNode.get("personAge").asText());
                nameCell.setCellValue(rowNode.get("firstName").asText());
                surnameCell.setCellValue(rowNode.get("lastName").asText());
                colNum = 0;
            }

            FileOutputStream outputStream = new FileOutputStream("D:/test.xlsx");
            wb.write(outputStream);
            wb.close();
            System.out.println(" Excel file generated");

        } catch (IOException e1) {
            logger.warning(e1.getMessage());
        }
    }

    public static void createJsonFile(String jsonData) throws IOException {
        FileWriter file = new FileWriter("D:/input.json");
        file.write(jsonData);
        file.close();
    }
}
