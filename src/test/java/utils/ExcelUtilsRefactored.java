package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Refactored Excel utility with better resource management and error handling
 */
public class ExcelUtilsRefactored {

    /**
     * Get total row count in a sheet
     *
     * @param filePath Excel file path
     * @param sheetName Sheet name
     * @return Row count
     * @throws IOException if file cannot be read
     */
    public static int getRowCount(String filePath, String sheetName) throws IOException {
        try (FileInputStream fi = new FileInputStream(filePath); XSSFWorkbook wb = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = wb.getSheet(sheetName);
            if (sheet == null) {
                LoggerUtil.warn("Sheet '" + sheetName + "' not found in file: " + filePath);
                return 0;
            }
            return sheet.getLastRowNum();
        } catch (IOException e) {
            LoggerUtil.error("Error reading row count from Excel file", e);
            throw e;
        }
    }

    /**
     * Get total column count in a specific row
     *
     * @param filePath Excel file path
     * @param sheetName Sheet name
     * @param rowNum Row number
     * @return Column count
     * @throws IOException if file cannot be read
     */
    public static int getCellCount(String filePath, String sheetName, int rowNum) throws IOException {
        try (FileInputStream fi = new FileInputStream(filePath); XSSFWorkbook wb = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = wb.getSheet(sheetName);
            if (sheet == null) {
                LoggerUtil.warn("Sheet '" + sheetName + "' not found");
                return 0;
            }
            XSSFRow row = sheet.getRow(rowNum);
            return row != null ? row.getLastCellNum() : 0;
        } catch (IOException e) {
            LoggerUtil.error("Error reading cell count from Excel file", e);
            throw e;
        }
    }

    /**
     * Read data from a specific cell
     *
     * @param filePath Excel file path
     * @param sheetName Sheet name
     * @param rowNum Row number
     * @param colNum Column number
     * @return Cell data as String
     * @throws IOException if file cannot be read
     */
    public static String getCellData(String filePath, String sheetName, int rowNum, int colNum)
            throws IOException {
        try (FileInputStream fi = new FileInputStream(filePath); XSSFWorkbook wb = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = wb.getSheet(sheetName);
            if (sheet == null) {
                LoggerUtil.warn("Sheet '" + sheetName + "' not found");
                return "";
            }
            XSSFRow row = sheet.getRow(rowNum);
            if (row == null) {
                LoggerUtil.debug("Row " + rowNum + " not found in sheet: " + sheetName);
                return "";
            }
            XSSFCell cell = row.getCell(colNum);
            if (cell == null) {
                LoggerUtil.debug("Cell [" + rowNum + "," + colNum + "] is empty");
                return "";
            }
            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);
        } catch (IOException e) {
            LoggerUtil.error("Error reading cell data from Excel file", e);
            throw e;
        }
    }

    /**
     * Write data to a specific cell
     *
     * @param filePath Excel file path
     * @param sheetName Sheet name
     * @param rowNum Row number
     * @param colNum Column number
     * @param data Data to write
     * @throws IOException if file cannot be written
     */
    public static void setCellData(String filePath, String sheetName, int rowNum, int colNum, String data)
            throws IOException {
        FileInputStream fi = null;
        FileOutputStream fo = null;

        try {
            File file = new File(filePath);

            // Create workbook
            XSSFWorkbook wb;
            if (file.exists()) {
                fi = new FileInputStream(file);
                wb = new XSSFWorkbook(fi);
            } else {
                wb = new XSSFWorkbook();
                // Create parent directories if they don't exist
                file.getParentFile().mkdirs();
            }

            // Get or create sheet
            XSSFSheet sheet = wb.getSheet(sheetName);
            if (sheet == null) {
                sheet = wb.createSheet(sheetName);
                LoggerUtil.debug("Created new sheet: " + sheetName);
            }

            // Get or create row
            XSSFRow row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }

            // Create and set cell value
            XSSFCell cell = row.createCell(colNum);
            cell.setCellValue(data);

            // Write to file
            fo = new FileOutputStream(file);
            wb.write(fo);
            wb.close();

            LoggerUtil.debug("Data written to [" + rowNum + "," + colNum + "] in sheet: " + sheetName);

        } catch (IOException e) {
            LoggerUtil.error("Error writing to Excel file", e);
            throw e;
        } finally {
            // Close resources properly
            if (fi != null) {
                try {
                    fi.close();
                } catch (IOException e) {
                    LoggerUtil.warn("Error closing FileInputStream: " + e.getMessage());
                }
            }
            if (fo != null) {
                try {
                    fo.close();
                } catch (IOException e) {
                    LoggerUtil.warn("Error closing FileOutputStream: " + e.getMessage());
                }
            }
        }
    }
}

