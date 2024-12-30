package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Book;
import model.User;
import utils.ExcelUtils;
import utils.DatabaseConnection;

import java.io.File;
import java.util.List;

public class BookUserExportController {
    @FXML
    private Button backButton;  // 添加返回按钮的FXML注入
    
    @FXML
    private void handleBookExport() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("导出图书数据");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel文件", "*.xlsx")
            );
            fileChooser.setInitialFileName("books.xlsx");
            
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                List<Book> books = DatabaseConnection.getAllBooks();
                ExcelUtils.exportBooks(books, file);
                showAlert(Alert.AlertType.INFORMATION, "导出成功", "图书数据已成功导出到Excel文件！");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "导出失败", "导出图书数据时发生错误：" + e.getMessage());
        }
    }
    
    @FXML
    private void handleUserExport() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("导出用户数据");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel文件", "*.xlsx")
            );
            fileChooser.setInitialFileName("users.xlsx");
            
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                List<User> users = DatabaseConnection.getAllUsers();//等待修复
                ExcelUtils.exportUsers(users, file);
                showAlert(Alert.AlertType.INFORMATION, "导出成功", "用户数据已成功导出到Excel文件！");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "导出失败", "导出用户数据时发生错误：" + e.getMessage());
        }
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent adminsystem = FXMLLoader.load(getClass().getResource("/views/adminsystem.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();  // 使用正确的按钮引用
            stage.setScene(new Scene(adminsystem));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "错误", "返回页面时发生错误：" + e.getMessage());
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
