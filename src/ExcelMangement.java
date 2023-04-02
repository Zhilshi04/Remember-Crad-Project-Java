import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class ExcelMangement {
    private FileInputStream fileInputStream;
    private String path;
    private Row row;
    private Cell cell;
    private Workbook workbook;
    private Sheet sheet;
    private FileOutputStream fileOut;
    public ExcelMangement(String path) throws IOException, InvalidFormatException {
        try {
            this.path = path;
            workbook = WorkbookFactory.create(new FileInputStream(path));
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
    public void addExcelSheet(String sheetName){
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
    public void writeExcelSheet(String sheetName,int rowIndex,int columnIndex,String data){
        try {
            workbook = WorkbookFactory.create(new FileInputStream(path));
            sheet = workbook.getSheet(sheetName);
            if(sheet.getRow(rowIndex) == null){
                row = sheet.createRow(rowIndex);
            }
            else{
                row = sheet.getRow(rowIndex);
            }
            cell = row.createCell(columnIndex);
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
    }
    public String ReadExcelSheet(String sheetName,int rowIndex,int columnIndex){
        String data = "";
        try {
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowIndex);
            cell = row.getCell(columnIndex);
            data = cell.getStringCellValue();
            workbook.close();
//            System.out.println("Read Success!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
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
    public static void main(String[] args) throws IOException, InvalidFormatException {
        Random rand = new Random();
        ExcelMangement DB = new ExcelMangement(".\\data\\database.xlsx");
        boolean run = true;
        Scanner scan = new Scanner(System.in);
        String sheetName = "";
        int row;
        int lastSheet = DB.getWorkbook().getNumberOfSheets()-1;
        while (run){
            System.out.println("You Enter Function : a is add Title ");
            System.out.println("You Enter Function : b is add word ");
            System.out.println("You Enter Function : q is Exit ");
            String check = scan.next();
            if(check.equalsIgnoreCase("a")){
                System.out.print("There are Title is you have : ");
                for(int i = 0;i<=(DB.getWorkbook().getNumberOfSheets()-1);i++){
                    System.out.print(DB.getWorkbook().getSheetName(i)+" ");
                }
                System.out.println();
                System.out.print("Your Enter New Title : ");
                String titel = scan.next().toLowerCase();
                DB.addExcelSheet(titel);
            }
            else if(check.equalsIgnoreCase("b")){
                System.out.print("There are Title is you have : ");
                for(int i = 0;i<=(DB.getWorkbook().getNumberOfSheets()-1);i++){
                    System.out.print(DB.getWorkbook().getSheetName(i)+" ");
                }
                System.out.println("Your Select title Name : ");
                sheetName = scan.next().toLowerCase();
                System.out.print("ใส่คำที่คุณต้องการจดจำ : ");
                String fontWord = scan.next();
                if(fontWord.equalsIgnoreCase("q"))break;
                System.out.print("คำที่เชื่อมโยง : ");
                String backWord = scan.next();
                row = DB.getWorkbook().getSheet(sheetName).getLastRowNum();
                if(backWord.equalsIgnoreCase("q"))break;
                DB.writeExcelSheet(sheetName,row,0,fontWord);
                DB.writeExcelSheet(sheetName,row,1,backWord);
                row++;
            }
            else if(check.equalsIgnoreCase("q"))break;
        }
    }
}
