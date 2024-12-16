package test;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader {
    public static List<Map<String, String>> readExcelData(String filePath, String sheetName) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            // Read the header row
            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue());
            }

            // Read the remaining rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Map<String, String> rowData = new HashMap<>();

                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);

                    // Handle different cell types
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                rowData.put(headers.get(j), cell.getStringCellValue());
                                break;
                            case NUMERIC:
                                rowData.put(headers.get(j), String.valueOf(cell.getNumericCellValue()));
                                break;
                            case BOOLEAN:
                                rowData.put(headers.get(j), String.valueOf(cell.getBooleanCellValue()));
                                break;
                            default:
                                rowData.put(headers.get(j), "");
                        }
                    } else {
                        rowData.put(headers.get(j), "");
                    }
                }

                data.add(rowData);
            }
        }

        return data;
    }
    
    public static Object[][] processExcelData(String filePath, String sheetName) throws IOException {
	    // Read the Excel data
	    List<Map<String, String>> testData = readExcelData(filePath, sheetName);

	    // Convert List<Map> into Object[][]
	    Object[][] data = new Object[testData.size()][1];
	    for (int i = 0; i < testData.size(); i++) {
	        data[i][0] = testData.get(i);
	    }
	    return data;
	}
}
