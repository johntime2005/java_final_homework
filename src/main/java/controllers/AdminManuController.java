package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class AdminManuController {
    @FXML
    private Button queryButton;
    
    @FXML
    private Button addBookButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private void handleQuery(ActionEvent event) {
        try {
            // 打开综合查询页面
            System.out.println("点击了综合查询按钮");
            Parent queryView = FXMLLoader.load(getClass().getResource("/views/query.fxml"));
            Stage stage = (Stage) queryButton.getScene().getWindow();
            stage.setScene(new Scene(queryView));
            stage.show();
            System.out.println("成功打开综合查询页面");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleAddBook(ActionEvent event) {
        try {
            // 打开添加书籍页面
            Parent addBookView = FXMLLoader.load(getClass().getResource("/views/addbook.fxml"));
            Stage stage = (Stage) addBookButton.getScene().getWindow();
            stage.setScene(new Scene(addBookView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // 返回登录页面
            Parent loginView = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
