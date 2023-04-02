import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelMangement {
    private FileInputStream fileInputStream;
    private String path;
    private Workbook workbook;
    private Sheet sheet;
    private FileOutputStream fileOut;
    public ExcelMangement(String path){
        this.path = path;
    }
    public void ConnectDB(){
        try {
            fileInputStream = new FileInputStream(path);
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet("default");
            fileInputStream.close();
            System.out.println("Success connect data base!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void AddExcelSheet(String sheetName){
        try {
            workbook = WorkbookFactory.create(new FileInputStream(path));
            sheet = workbook.createSheet(sheetName);
            Row headerRow = sheet.createRow(0);
            Cell headerCell = headerRow.createCell(0);
            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Add Sheet Success!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public String WriteExcelSheet(String sheetName,int rowIndex,int columnIndex,String data){
        try {
            workbook = WorkbookFactory.create(new FileInputStream(path));
            sheet = workbook.getSheet(sheetName);
            Row row = sheet.createRow(rowIndex);
            Cell cell = row.createCell(columnIndex);
            cell.setCellValue(data);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Write Success!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Workbook getWorkbook() {
        return workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }

    public String getPath() {
        return path;
    }
}
class MainX{
    public static void main(String[] args){
        ExcelMangement DB = new ExcelMangement(".\\data\\database.xlsx");

    }
}
