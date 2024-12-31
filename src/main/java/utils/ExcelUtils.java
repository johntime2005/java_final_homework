package utils;

import model.Book;
import model.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import java.io.IOException;
public class ExcelUtils {
    
//    public static List<Book> importBooks(File file) throws Exception {
//        List<Book> books = new ArrayList<>();
//        FileInputStream fis = new FileInputStream(file);
//        Workbook workbook;
//
//        if (file.getName().endsWith("xlsx")) {
//            workbook = new XSSFWorkbook(fis);
//        } else {
//            workbook = new HSSFWorkbook(fis);
//        }
//
//        Sheet sheet = workbook.getSheetAt(0);
//        int lastRowNum = sheet.getLastRowNum();
//
//        for (int i = 1; i <= lastRowNum; i++) {
//            Row row = sheet.getRow(i);
//            Book book = new Book();
//            book.setName(row.getCell(0).getStringCellValue());
//            book.setAuthor(row.getCell(1).getStringCellValue());
//            book.setPress(row.getCell(2).getStringCellValue());
//            book.setIsborrowed(false); // 新书默认未借出
//            books.add(book);
//        }
//
//        workbook.close();
//        fis.close();
//        return books;
//    }



        public static List<Book> importBooks(File file) throws IOException {
            List<Book> books = new ArrayList<>();
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook;

            if (file.getName().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else {
                workbook = new HSSFWorkbook(fis);
            }

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();

            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue; // Skip empty rows
                }

                Book book = new Book();
                book.setName(getCellValueAsString(row.getCell(0)));
                book.setAuthor(getCellValueAsString(row.getCell(1)));
                book.setPress(getCellValueAsString(row.getCell(2)));
                book.setIsborrowed(false); // 新书默认未借出
                books.add(book);
            }

            workbook.close();
            fis.close();
            return books;
        }

        private static String getCellValueAsString(Cell cell) {
            if (cell == null) {
                return "";
            }
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    } else {
                        return String.valueOf(cell.getNumericCellValue());
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    return cell.getCellFormula();
                default:
                    return "";
            }
        }


    public static void exportBooks(List<Book> books, File file) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Books");
        
        // 创建标题行
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("书名");
        headerRow.createCell(2).setCellValue("作者");
        headerRow.createCell(3).setCellValue("出版社");
        headerRow.createCell(4).setCellValue("借阅状态");
        
        // 写入数据
        int rowNum = 1;
        for (Book book : books) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(book.getId());
            row.createCell(1).setCellValue(book.getName());
            row.createCell(2).setCellValue(book.getAuthor());
            row.createCell(3).setCellValue(book.getPublisher());
            row.createCell(4).setCellValue(book.getIsborrowed() ? "已借出" : "未借出");
        }
        
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        workbook.close();
        fos.close();
    }
    
    public static List<User> importUsers(File file) throws Exception {
        List<User> users = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook;
        
        if (file.getName().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(fis);
        } else {
            workbook = new HSSFWorkbook(fis);
        }
        
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            String username = row.getCell(0).getStringCellValue();
            String password = row.getCell(1).getStringCellValue();
            String userType = row.getCell(2).getStringCellValue();
            int balance = (int) row.getCell(3).getNumericCellValue();
            
            users.add(new User(username, password, userType, balance));
        }
        
        workbook.close();
        fis.close();
        return users;
    }
    
    public static void exportUsers(List<User> users, File file) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");
        
        // 创建标题行
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("用户名");
        headerRow.createCell(2).setCellValue("密码");
        headerRow.createCell(3).setCellValue("用户类型");
        headerRow.createCell(4).setCellValue("账户余额");
        
        // 写入数据
        int rowNum = 1;
        for (User user : users) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getUsername());
            row.createCell(2).setCellValue(user.getPassword());
            row.createCell(3).setCellValue(user.getUserType());
            row.createCell(4).setCellValue(user.getBalance());
        }
        
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        workbook.close();
        fos.close();
    }
}
