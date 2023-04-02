import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Scanner;
class ConnetDatabase{
    protected Workbook workbook;
    protected Sheet sheet;
    protected String path;
    protected ConnetDatabase(){

    }
    public ConnetDatabase(String path){
        this.path = path;
        try{
            FileInputStream fileInputStream = new FileInputStream(new File(this.path));
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet("default");
            fileInputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public Sheet getSheet() {
        return sheet;
    }
    public String getPath() {
        return path;
    }
    public Workbook getWorkbook() {
        return workbook;
    }
}
class AddExcelSheet {
    public AddExcelSheet(String path,String sheetName) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(path)); // Load existing workbook
        Sheet sheet = workbook.createSheet(sheetName); // Create new sheet
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        FileOutputStream fileOut = new FileOutputStream(path); // Create output stream
        workbook.write(fileOut); // Write workbook to output stream
        fileOut.close(); // Close output stream
        workbook.close();
    }
}
class WriteExcelFile {
        public  WriteExcelFile(String path,String sheetName){
            try {
                // Create a new workbook
                Workbook workbook = WorkbookFactory.create(new FileInputStream(path));

                // Create a new sheet
                Sheet sheet = workbook.getSheet(sheetName);

                // Create a header row
                Row headerRow = sheet.createRow(sheet.getLastRowNum());
                Cell headerCell = headerRow.createCell(0);
                headerCell.setCellValue("Name");

                // Create a data row
                Row dataRow = sheet.createRow(sheet.getLastRowNum());
                Cell dataCell = dataRow.createCell(1);
                dataCell.setCellValue("John Doe");
                dataCell.setCellValue("");

                // Write the workbook to a file
                FileOutputStream outputStream = new FileOutputStream(path);
                workbook.write(outputStream);

                // Close the workbook and output stream
                workbook.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
        }

}
class Main{
    public static void main(String[] args) throws IOException, InvalidFormatException {
        ConnetDatabase conDB = new ConnetDatabase(".\\data\\database.xlsx");
        WriteExcelFile writeDB = new WriteExcelFile(".\\data\\database.xlsx","Apple");
    }
}
