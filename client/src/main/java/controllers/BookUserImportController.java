package controllers;

import utils.BookService;
import utils.userService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import javafx.event.ActionEvent;

import impl.bookMegement;
import impl.userMegement;
import model.Book;

import java.util.ArrayList;
import java.util.List;

import utils.ExcelUtils;
import javafx.scene.control.Alert;
import javafx.scene.Node;

import model.User;

public class BookUserImportController {
    private BookService bookService;
    private userService userService;

    public BookUserImportController() {
        this.bookService = new BookService();
        this.userService = new userService(new userMegement(), this.bookService);
    }

    @FXML
    private Button UserButton;

    @FXML
    private Button BookButton;

    @FXML
    private void handleUser(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择用户文件");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls")
            );
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            
            if (file != null) {
                List<User> users = ExcelUtils.importUsers(file);
                for (User user : users) {
                    userService.create(user);
                }
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("成功");
                alert.setHeaderText(null);
                alert.setContentText("成功导入 " + users.size() + " 个用户！");
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("导入失败：" + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBook(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择书籍文件");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls")
            );
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            
            if (file != null) {
                List<Book> books = ExcelUtils.importBooks(file);
                bookService.batchAddBooks(books);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("成功");
                alert.setHeaderText(null);
                alert.setContentText("成功导入 " + books.size() + " 本书籍！");
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("导入失败：" + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // 返回管理员菜单页面
            Parent adminsystem = FXMLLoader.load(getClass().getResource("/views/adminsystem.fxml"));
            Stage stage = (Stage) UserButton.getScene().getWindow();
            stage.setScene(new Scene(adminsystem));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}