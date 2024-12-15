package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class ExcelReader {

    private static String excelFilePath;
    public ExcelReader(String excelFilePath)
    {
    	this.excelFilePath=excelFilePath;
    }
    public static Object getCellData(String excelFilePath, int rowNum, int colNum) {
        Object cellData = null;

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Access the sheet (by index or name)
            Sheet sheet = workbook.getSheetAt(0);

            // Get the row based on rowNum
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                // Get the cell based on colNum
                Cell cell = row.getCell(colNum);
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case STRING:
                            cellData = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            // If it's a date, convert it
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cellData = cell.getDateCellValue();
                            } else {
                                cellData = cell.getNumericCellValue();
                            }
                            break;
                        case BOOLEAN:
                            cellData = cell.getBooleanCellValue();
                            break;
                        case FORMULA:
                            cellData = cell.getCellFormula(); // Formula expression
                            break;
                        default:
                            cellData = "Unsupported cell type";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellData;
    }
    public static Object getCellData(int rowNum, int colNum) {
        Object cellData = null;

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Access the sheet (by index or name)
            Sheet sheet = workbook.getSheetAt(0);

            // Get the row based on rowNum
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                // Get the cell based on colNum
                Cell cell = row.getCell(colNum);
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case STRING:
                            cellData = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            // If it's a date, convert it
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cellData = cell.getDateCellValue();
                            } else {
                                cellData = cell.getNumericCellValue();
                            }
                            break;
                        case BOOLEAN:
                            cellData = cell.getBooleanCellValue();
                            break;
                        case FORMULA:
                            cellData = cell.getCellFormula(); // Formula expression
                            break;
                        default:
                            cellData = "Unsupported cell type";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellData;
    }
}