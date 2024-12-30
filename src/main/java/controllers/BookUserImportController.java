package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import javafx.event.ActionEvent;

import dao.bookMegementDao;
import model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookUserImportController {

    @FXML
    private Button UserButton;

    @FXML
    private Button BookButton;

    @FXML
    private void handleUser(ActionEvent event) {
        try {
            //打开文件选择器
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择用户文件");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("excel", "*.xls", "*.xlsx"));
            Stage stage = (Stage) UserButton.getScene().getWindow();
            fileChooser.showOpenDialog(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //导入用户
        
    }

    @FXML
    private void handleBook(ActionEvent event) {
        List<Book> books = new ArrayList<>();
        File file = null;
        try {
            // 打开书籍导入页面
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择书籍文件");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("excel", "*.xls", "*.xlsx"));
            Stage stage = (Stage) BookButton.getScene().getWindow();
            fileChooser.showOpenDialog(stage);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        //导入书籍
        books = ExcelUtil.readBooksFromExcel(file);
        bookMegementDao.importBooks(books);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // 返回管理员菜单页面
            Parent adminManuView = FXMLLoader.load(getClass().getResource("/views/adminmanu.fxml"));
            Stage stage = (Stage) UserButton.getScene().getWindow();
            stage.setScene(new Scene(adminManuView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}