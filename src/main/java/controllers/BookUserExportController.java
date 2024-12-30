package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import model.Book;
import model.User;
import utils.ExcelUtils;
import utils.DatabaseConnection;

import java.io.File;
import java.util.List;

public class BookUserExportController {
    
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
                List<User> users = DatabaseConnection.getAllUsers();
                ExcelUtils.exportUsers(users, file);
                showAlert(Alert.AlertType.INFORMATION, "导出成功", "用户数据已成功导出到Excel文件！");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "导出失败", "导出用户数据时发生错误：" + e.getMessage());
        }
    }
    
    @FXML
    private void handleBack() {
        // 返回主界面
        SceneManager.loadScene("mainview.fxml");
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
