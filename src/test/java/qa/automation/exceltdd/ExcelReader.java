package qa.automation.exceltdd;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    private String excelFilePath;
    private XSSFSheet sheet;
    private XSSFWorkbook workbook;
    private String sheetName;

    public ExcelReader(String excelFilePath) {
        this.excelFilePath = excelFilePath;
        File file = new File(excelFilePath);
        try (FileInputStream fileInputStream = new FileInputStream(file)){
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet("Лист1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ExcelReader(String excelFilePath, String sheetName) {
        this.excelFilePath = excelFilePath;
        this.sheetName = sheetName;
        File file = new File(excelFilePath);
        try (FileInputStream fileInputStream = new FileInputStream(file)){
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[][] getSheetDataForTDD() throws IOException {
        File file = new File(excelFilePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet("Лист1");
        int numOfColumns = xlsxCountColumn();
        int numOfRows = xlsxCountRaw();
        String[][] data = new String[numOfRows - 1][numOfColumns];
        for (int i = 1; i < numOfRows; i++) {
            for (int j = 0; j < numOfColumns; j++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(j);
                String cellValue = callToString(cell);
                data[i - 1][j] = cellValue;
                if (cellValue == null) {
                    System.out.println("Empty cell");
                }
            }
        }
        fileInputStream.close();
        return data;
    }

    public String[][] getCustomSheetDataForTDD() throws IOException {
        File file = new File(excelFilePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(sheetName);
        int numOfColumns = xlsxCountColumn();
        int numOfRows = xlsxCountRaw();
        String[][] data = new String[numOfRows - 1][numOfColumns];
        for (int i = 1; i < numOfRows; i++) {
            for (int j = 0; j < numOfColumns; j++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(j);
                String cellValue = callToString(cell);
                data[i - 1][j] = cellValue;
                if (cellValue == null) {
                    System.out.println("Empty cell");
                }
            }
        }
        fileInputStream.close();
        return data;
    }

    private String callToString(XSSFCell cell) {
        Object result;
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC -> result = cell.getNumericCellValue();
            case STRING -> result = cell.getStringCellValue();
            case BLANK -> result = "";
            case BOOLEAN -> result = cell.getBooleanCellValue();
            case FORMULA -> result = cell.getCellFormula();
            default -> throw new IllegalStateException("Unexpected value: " + cellType);
        }
        return result.toString();
    }

    private int xlsxCountColumn() {
        return sheet.getRow(0).getLastCellNum();
    }

    private int xlsxCountRaw() {
        return sheet.getLastRowNum() + 1;
    }
}





